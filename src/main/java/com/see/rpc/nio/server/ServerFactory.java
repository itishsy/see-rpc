package com.see.rpc.nio.server;

import com.see.rpc.cache.ServerCache;
import com.see.rpc.common.util.NetUtil;
import com.see.rpc.nio.Server;


public class ServerFactory {

    private static class Singleton {
        private static final ServerFactory instance = new ServerFactory();
    }

    public static ServerFactory getInstance() {
        return Singleton.instance;
    }

    private Server server = null;

    /**
     * 启动一个服务(netty)
     */
    public void start() {
        String host = NetUtil.localAddress();
        int port = NetUtil.fetchPort();
        server = new NettyServer();
        server.start(host,port);
        ServerCache.address = host + ":" + port;
    }

    public void destroy(){
        server.shutdown();
        ServerCache.clear();
    }
}
