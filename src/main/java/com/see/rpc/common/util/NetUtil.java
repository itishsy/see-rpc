package com.see.rpc.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;

public class NetUtil {

    public static String localAddress() {
        try {
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                return windowsMacIpAddr();
            }else if(System.getProperty("os.name").toUpperCase().contains("mac")){
                return windowsMacIpAddr();
            }else{
                return othersIpAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int fetchPort() {
        int port = System.getProperty("port") == null?0:Integer.parseInt(System.getProperty("port"));
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            port = serverSocket.getLocalPort();
            System.out.println("配置端口可用：" + port);
        } catch (Exception e) {
            try {
                serverSocket = new ServerSocket(0);
                port = serverSocket.getLocalPort();
                System.out.println("生成随机端口：" + port);
            } catch (Exception e1){
                e1.printStackTrace();
            }
        } finally {
            close(serverSocket);
        }
        return port;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * windows ip
     * @return
     * @throws Exception
     */
    private static String windowsMacIpAddr()throws Exception{
        return InetAddress.getLocalHost().getHostAddress();
    }


    /**
     * 获取linux unix 为内核的ip
     * @return
     * @throws Exception
     */
    private static String othersIpAddr()throws Exception{
        String ipString = "";
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            //此处判断兼容了unix 和 linux
            if(netInterface.getName().startsWith("e")){
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && !ip.getHostAddress().equals("127.0.0.1")) {
                        return ip.getHostAddress();
                    }
                }
            }
        }
        return ipString;
    }

}
