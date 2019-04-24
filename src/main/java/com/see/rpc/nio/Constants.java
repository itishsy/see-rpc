package com.see.rpc.nio;

/**
 * Created by Administrator on 2018/12/12.
 */
public class Constants {
    /**
     * rpc server thread pool
     */
    public static final String SERVER_WORKER_THREAD_NAME = "rpc-netty-worker-pool-thread-";
    public static final int SERVER_WORKER_THREAD_NUMBER = Runtime.getRuntime().availableProcessors();

    /**
     * server task execute pool
     */
    private static final String SERVER_DEFAULT_THREADS_PROPERTY = "default-threads";   //jvm启动参数可配置
    private static final String SERVER_QUEUE_CAPACITY_PROPERTY = "queue-capacity";   //jvm启动参数配可置
    public static final String SERVER_HANDLE_THREAD_NAME = "rpc-server-execute-pool-thread-";
    public static final int SERVER_HANDLE_EXECUTE_DEFAULT_THREAD_SIZE = Integer.getInteger(SERVER_DEFAULT_THREADS_PROPERTY, 20);
    public static final int SERVER_HANDLE_EXECUTE_MAXIMUM_QUEUE_CAPACITY = Integer.getInteger(SERVER_QUEUE_CAPACITY_PROPERTY, 1000);

    /**
     * 服务端
     */
    public static final String SERVER_HEALTH_STATUS_THREAD_NAME = "rpc-server-health-status-";

    /**
     * 重连配置
     */
    public static final String RECONNECT_HANDLE_THREAD_NAME = "rpc-reconnect-execute-pool-thread-";
    public static final int DEFAULT_RECONNECT_SIZE = 5;
    public static final int START_RECONNECT_AFTER_LOST_HAERTBEAT_SIZE = 3;
}
