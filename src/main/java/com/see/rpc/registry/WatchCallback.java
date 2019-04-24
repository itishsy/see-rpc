package com.see.rpc.registry;

/**
 * Created by Administrator on 2018/12/9.
 */
public interface WatchCallback {

    void onAdd(ServerNode serverNode);

    void onRemove(ServerNode serverNode);
}
