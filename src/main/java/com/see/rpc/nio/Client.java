package com.see.rpc.nio;
import com.see.rpc.nio.base.Request;
import com.see.rpc.nio.base.Response;
import com.see.rpc.nio.base.ResponseFuture;

/**
 * Created by Administrator on 2018/12/8.
 */
public interface Client {

    boolean connect();

    ResponseFuture send(Request request);

    String remoteAddress();

    boolean connected();

    void shutdown();
}
