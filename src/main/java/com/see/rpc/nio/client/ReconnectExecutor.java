package com.see.rpc.nio.client;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.see.rpc.nio.Client;
import com.see.rpc.nio.Constants;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.see.rpc.cache.ClientCache;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ReconnectExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ReconnectExecutor.class);

    private ListeningExecutorService executorService;

    private static Map<String,AtomicInteger> reconnectMap = Maps.newConcurrentMap();


    private ReconnectExecutor() {
        executorService = MoreExecutors
                .listeningDecorator(Executors.newSingleThreadExecutor(new DefaultThreadFactory(Constants.RECONNECT_HANDLE_THREAD_NAME)));
    }

    private static ReconnectExecutor executor;

    public static ReconnectExecutor getInstance() {
        if (executor == null) {
            synchronized (ReconnectExecutor.class) {
                if (executor == null) {
                    executor = new ReconnectExecutor();
                }
            }
        }
        return executor;
    }


    @SuppressWarnings("unchecked")
    public void reconnect(String host, int port) {

        Client client = ClientCache.getClient(host+":"+port);
        if(client == null || client.connected()) return;

        final String address = host + ":" + port;
        reconnectMap.put(address,new AtomicInteger(Constants.DEFAULT_RECONNECT_SIZE));
        executorService.submit(() -> {
            while (allowReconnect(address)) {
                try {
                    if (client.connect()) {
                        reconnectMap.remove(client.toString());
                        logger.info("重连成功。" + client);
                        return true;
                    } else {
                        logger.debug("重连失败。" + client);
                    }
                } catch (Exception e) {
                    logger.debug( "重连失败。" + e.getMessage());
                }
            }
            logger.debug( "重连任务结束。" );
            return false;
        });
    }

    private boolean allowReconnect(String address) {
        int size = reconnectMap.get(address).decrementAndGet();
        if (size > 0) {
            try {
                Thread.sleep((Constants.DEFAULT_RECONNECT_SIZE - size) * 5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(null != ClientCache.getClient(address)) {
                logger.debug("执行第" + (Constants.DEFAULT_RECONNECT_SIZE - size) + "次重连...." + address);
                return true;
            }
        }
        return false;
    }
}
