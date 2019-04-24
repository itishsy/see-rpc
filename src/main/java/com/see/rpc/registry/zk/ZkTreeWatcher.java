package com.see.rpc.registry.zk;

import com.see.rpc.registry.ServerNode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.see.rpc.registry.WatchCallback;

public class ZkTreeWatcher implements TreeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(ZkTreeWatcher.class);

    private WatchCallback callback;

    public ZkTreeWatcher(WatchCallback callback) {
        this.callback = callback;
    }

    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        ServerNode serverNode;
        logger.debug("zk client event " + event.getType() + " fire .");
        switch (event.getType()) {
            case NODE_ADDED:
                serverNode = fetch(client, event.getData().getPath());
                if (null != serverNode)
                    callback.onAdd(serverNode);
                break;
            case NODE_REMOVED:
                serverNode = fetch(client, event.getData().getPath());
                if (null != serverNode)
                    callback.onRemove(serverNode);
            default:
                break;
        }
    }

    private ServerNode fetch(CuratorFramework client,String path) throws Exception {
        ServerNode serverNode = null;
        String[] nodes = path.split("/");
        if (nodes.length != 5) return serverNode;

        String tokenPath = path.substring(0, path.lastIndexOf("/"));
        byte[] bytes = client.getData().forPath(tokenPath);
        if (bytes == null || bytes.length == 0) return serverNode;

        serverNode = new ServerNode(nodes[3], bytes, nodes[4]);
        return serverNode;
    }

}
