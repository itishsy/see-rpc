package com.see.rpc.nio.server;


import com.google.common.util.concurrent.*;
import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.nio.Constants;
import com.see.rpc.nio.base.Message;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrySupport;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    private ExecutorService pool = Executors.newFixedThreadPool(PropertyConfiguration.MAX_THREADS,
            new DefaultThreadFactory("rpc-server-execute-pool-thread"));

    private TaskExecutor() {
    }

    private ListeningExecutorService executorService = MoreExecutors
            .listeningDecorator(pool);

    private volatile static TaskExecutor executor;

    public static TaskExecutor getInstance() {
        if (executor == null) {
            synchronized (TaskExecutor.class) {
                if (executor == null) {
                    logger.info("创建任务处理线程池，线程数：" + PropertyConfiguration.MAX_THREADS);
                    executor = new TaskExecutor();
                }
            }
        }
        return executor;
    }

    public void execute(Callable<Message> callable, FutureCallback<Message> callback) {
        ListenableFuture<Message> listenableFuture = executorService.submit(callable);
        Futures.addCallback(listenableFuture, callback, executorService);
    }

    public ExecutorService getPool() {
        return pool;
    }
}
