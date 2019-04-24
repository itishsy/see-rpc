package com.see.rpc;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Controller相当于一个web服务器，接收请求命令，执行对应service
 * Cmd是一个控制台,用于向Controller发送一段简单命令。
 * 输入格式为 beanName.methodName
 * 如：userService.findAllUser
 *
 * 也可以用其他tcp工具代替，如 sokit，默认端口 1111
 */
public class Cmd {

    public static void main1(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1111);
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            while (true){
                OutputStream os = socket.getOutputStream();
                PrintWriter writer =new PrintWriter(os);
                writer.write(cmd);
                writer.flush();
                cmd = scanner.nextLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final Cmd test = new Cmd();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }
            }.start();
        }

        while (Thread.activeCount() > 1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }

}
