package com.see.rpc.nio;

/**
 * Created by Administrator on 2018/12/8.
 */
public interface Server {

    void start(String host,int port);

    void shutdown();
}
