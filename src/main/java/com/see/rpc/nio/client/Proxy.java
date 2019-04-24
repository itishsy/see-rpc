package com.see.rpc.nio.client;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;
import com.see.rpc.cluster.Limit;
import com.see.rpc.cluster.LoadBalance;
import com.see.rpc.cluster.LoadBalanceFactory;
import com.see.rpc.common.exceptions.RpcException;
import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.nio.Client;
import com.see.rpc.nio.base.Request;
import com.see.rpc.nio.base.Response;
import com.see.rpc.nio.base.ResponseFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/12/9.
 */
public class Proxy {
    private static final Logger logger = LoggerFactory.getLogger(Proxy.class);

    @SuppressWarnings("unchecked")
    public static Builder create(Class<?> clazz) {
        return new Builder(clazz);
    }

    public static class Builder<T> {

        private final Class<T> clazz;

        private String scope;

        private long timeout;

        private Builder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }


        public T build() {
            return Reflection.newProxy(clazz, new AbstractInvocationHandler() {
                @Override
                protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
                    Request request = new Request();
                    request.setTimeout(timeout);
                    request.setService(clazz.getName());
                    request.setMethod(method.getName());
                    request.setArgs(args);
                    return Proxy.getInstance().process(request);
                }
            });
        }
    }

    private static class Singleton {
        public static final Proxy instance = new Proxy();
    }

    public static Proxy getInstance() {
        return Singleton.instance;
    }

    private Object process(Request request) throws Throwable {
        Set<Client> clients = ClientFactory.getInstance().getClients(request.getService());
        if(clients == null || clients.size() == 0){
            throw new RpcException("remote server not found");
        }
        ResponseFuture future = clientSend(clients, request);
        if(future == null){
            throw new RpcException("remote server io exception");
        }

        return getResult(future);
    }

    private ResponseFuture clientSend(Set<Client> clients,Request request){
        Client client;
        if(clients.size() == 1){
            client= clients.iterator().next();
        } else {
            Limit limit = LimitFactory.valueOf(PropertyConfiguration.LOAD_BALANCE);
            LoadBalance loadBalance = LoadBalanceFactory.valueOf(PropertyConfiguration.LOAD_BALANCE).limit(limit).instance();
            client = loadBalance.select(clients);
        }
        ResponseFuture future = null;
        try {
            future = client.send(request);
        } catch (RpcException e) {
            logger.warn("rpc exception. try next client .", e);
            if (clients.size() > 1) {
                Set<Client> nextClients = new HashSet<>(clients);
                nextClients.remove(client);
                return clientSend(nextClients, request);
            }
        }
        return future;
    }

    private Object getResult(ResponseFuture future) throws Throwable {
        Response response = future.get();
        if(response == null){
            throw new RpcException("request time out");
        }

        Throwable throwable;
        if ((throwable = response.getThrowable()) == null) {
            return response.getResult();
        }

        throwable.printStackTrace();
        throw throwable;
    }

}
