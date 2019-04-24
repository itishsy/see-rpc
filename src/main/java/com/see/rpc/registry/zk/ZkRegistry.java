package com.see.rpc.registry.zk;

import com.see.rpc.registry.Constants;
import com.see.rpc.registry.Registry;
import com.see.rpc.registry.WatchCallback;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/12/9.
 */
public class ZkRegistry implements Registry {
    private static final Logger logger = LoggerFactory.getLogger(ZkRegistry.class);
    private String address;

    private CountDownLatch latch = new CountDownLatch(1);
    private volatile CuratorFramework curatorFramework;

    public ZkRegistry(String address){
        this.address = address;
        connect();
    }

    @Override
    public void connect() {
        if (this.curatorFramework != null) {
            return;
        }
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(Constants.REGISTRY_SESSION_TIMEOUT_MS)
                .connectionTimeoutMs(Constants.REGISTRY_CONNECTION_TIMEOUT_MS)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        this.curatorFramework.getConnectionStateListenable().addListener((CuratorFramework client, ConnectionState newState) -> {
            if (ConnectionState.CONNECTED.equals(newState)) {
                latch.countDown();
            }
            logger.info("注册中心连接状态：" + newState.isConnected());
        });
        this.curatorFramework.start();
    }

    private CuratorFramework getCuratorFramework() {
        try {
            latch.await(Constants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return curatorFramework;
    }

    @Override
    public void watch(String node, final WatchCallback watchCallback) {
        try {
            TreeCache treeCache = TreeCache.newBuilder(getCuratorFramework(), node).setMaxDepth(2).build();
            treeCache.start();
            treeCache.getListenable().addListener(new ZkTreeWatcher(watchCallback));
        } catch (Exception e) {
            logger.warn(node + " add listener error " + e.getMessage(), e);
        }
    }

    @Override
    public void persistent(String node, byte[] data) {
        try {
            if (getCuratorFramework().checkExists().forPath(node) == null) {
                getCuratorFramework().create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(node,data);
            }
        } catch (Exception e) {
            logger.error(" register persistent node error " + node, e);
        }
    }

    @Override
    public void ephemeral(String node) {
        try {
            if (getCuratorFramework().checkExists().forPath(node) == null) {
                PersistentEphemeralNode ephemeralNode = new PersistentEphemeralNode(getCuratorFramework(), PersistentEphemeralNode.Mode.EPHEMERAL,node,new byte[0]);
                ephemeralNode.start();
                ephemeralNode.waitForInitialCreate(Constants.REGISTRY_SESSION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            logger.error(" register ephemeral node error. " + node, e);
        }
    }

    @Override
    public void delete(String node) {
        try {
             getCuratorFramework().delete().forPath(node);
        } catch (Exception e) {
            logger.warn(" delete node error " + node, e);
        }
    }

    @Override
    public void destroy() {
        if (getCuratorFramework() != null) {
            getCuratorFramework().close();
        }
    }
}
