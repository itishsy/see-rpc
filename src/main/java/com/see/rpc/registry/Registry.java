package com.see.rpc.registry;

/**
 * Created by Administrator on 2018/12/9.
 */
public interface Registry {
    void connect();

    void watch(String node, WatchCallback watchCallback);

    void persistent(String node, byte[] data);

    void ephemeral(String node);

    void delete(String node);

    void destroy();
}
