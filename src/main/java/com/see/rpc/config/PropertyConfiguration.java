package com.see.rpc.config;

public class PropertyConfiguration {

    /**
     * 负载策略
     * 配置参数：loadBalance
     * 配置选项：PriorityRoundRobin（默认）\RoundRobin\WeightRoundRobin
     */
    public static String LOAD_BALANCE = System.getProperty("loadBalance", "PriorityRoundRobin");


    /**
     * 请求超时时间
     * 配置参数：rpcTimeout
     * 配置选项：30分种（默认）\其他数值，单位豪秒
     */
    public static final long RPC_TIMEOUT = Long.getLong("rpcTimeout", 30 * 60 * 1000L);

    /**
     * 应用环境
     * 配置参数：p
     * 配置选项：development（默认）\functional、production、uat等
     */
    public static String PROFILE = System.getProperty("p", "development");

    /**
     * 应用名称
     * 配置参数：appName
     * 配置选项：空（默认）\system\customer\等
     */
    public static String AppName = System.getProperty("app-name", "");

    /**
     * 最大线程数
     * 配置参数：maxThreads
     * 配置选项：20（默认）
     */
    public static int MAX_THREADS = Integer.valueOf(System.getProperty("maxThreads", Math.max(20, Runtime.getRuntime().availableProcessors()) + ""));

    /**
     * 表示服务压力的队列任务数
     * 配置参数：stressedSize
     * 配置选项：300（默认）\其他数值等
     */
    public static int STRESSED_SIZE = Integer.valueOf(System.getProperty("stressedSize", "300"));
}
