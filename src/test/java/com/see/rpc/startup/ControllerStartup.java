package com.see.rpc.startup;

import com.see.rpc.common.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.*;
import java.lang.System;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.*;

public class ControllerStartup {
    private static final Logger logger = LoggerFactory.getLogger(ControllerStartup.class);

    private static BlockingQueue<String> inputQueue = new LinkedBlockingDeque<>();

    private static GenericXmlApplicationContext applicationContext = null;

    public static void main(String[] args) {
        startSocketServer();
        if(args!=null && args.length>0) {
            for (int i = 0; i < args.length; i = i + 2) {
                System.setProperty(args[i].replace("-", ""), args[i + 1]);
            }
        }
        applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("classpath:spring-apps/spring-controller.xml");
        applicationContext.refresh();
        start();
    }

    private static void start() {
        try {
            String cmd = inputQueue.take();
            while (!"stop".equals(cmd)) {
                try {
                    String[] arr = cmd.split("\\.");
                    Object bean = ContextHolder.getBean(arr[0]);

                    if (arr.length < 2) {
                        logger.info("接收到无效命令：" + cmd);
                        continue;
                    }
                    if (arr.length == 2) {
                        new Thread(()->{
                            try {
                                Object ret = bean.getClass().getDeclaredMethod(arr[1]).invoke(bean);
                                logger.info("返回结果：" + ((ret instanceof Collection) ? ((Collection) ret).size() : ""));
                                logger.info("" + ret);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } else {
                        int argsLen = arr.length - 2;
                        Class<?>[] argsTypes = new Class<?>[argsLen];
                        String[] args = new String[argsLen];
                        for (int i = 0; i < argsLen; i++) {
                            argsTypes[i] = String.class;
                            args[i] = arr[2 + i];
                        }
                        try {
                            Object ret = bean.getClass().getDeclaredMethod(arr[1], argsTypes).invoke(bean, args);
                            logger.info("返回结果：" + ((ret instanceof Collection) ? ((Collection) ret).size() : ""));
                            logger.info("" + ret);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                cmd = inputQueue.take();
            }
            if("stop".equals(cmd)){
                applicationContext.stop();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startSocketServer() {
        new Thread(() -> {
                ServerSocket server =  null;
                try {
                    String port = "1111";
                    server = new ServerSocket(Integer.valueOf(port));
                    logger.info(" ====== Socket服务启动，监听端口［"+port+"］ ====== ");
                    while (true) {
                        final Socket socket = server.accept();
                        new Thread(() -> {
                            while (true){
                                try {
                                    InputStream inputStream = socket.getInputStream();
                                    byte[] bytes = new  byte[128];
                                    inputStream.read(bytes);
                                    String cmd = new String(bytes);
                                    logger.info("接收到一个命令：" + cmd.trim());
                                    inputQueue.put(cmd.trim());
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(server!=null){
                        try {
                            server.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }).start();
    }
}
