package com.see.rpc.spring;

import com.see.rpc.cache.ClientCache;
import com.see.rpc.cache.ServerCache;
import com.see.rpc.common.exceptions.RpcException;
import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.nio.client.Proxy;
import com.see.rpc.nio.server.ServerFactory;
import com.see.rpc.registry.RegistryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 客户端启动加载
 */
public class RpcBootstrap extends AbstractBootstrap {
    private String hosts;

    @Autowired
    private RpcBeanFactory factory;

    public void setRpcService( Object bean) {
        Class beanClass = bean.getClass();
        if (AopUtils.isCglibProxy(bean) || AopUtils.isAopProxy(bean) || AopUtils.isJdkDynamicProxy(bean)) {
            beanClass = AopUtils.getTargetClass(bean);
        }
        RpcService rpcService = (RpcService)  beanClass.getAnnotation(RpcService.class);
        if(rpcService == null) return;

        if(!Unregister.class.isAssignableFrom(rpcService.qualifier())){
            ServerCache.put(rpcService.qualifier().getName(), bean);
        } else {
            Class<?>[] interfacesClasses = beanClass.getInterfaces();
            if (interfacesClasses == null || interfacesClasses.length == 0) {
                throw new RpcException("service bean:" + beanClass.getName() + "未实现接口");
            }
            for (Class<?> clazz : interfacesClasses) {
                if (!Unregister.class.isAssignableFrom(clazz)) {
                    ServerCache.put(clazz.getName(),bean);
                }
            }
        }
    }

    public void setRpcResourceProxy(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(f -> f.getAnnotation(RpcResource.class) != null).forEach(field -> {
            field.setAccessible(true);
            try {
                if (null == field.get(bean)) {
                    Class<?> clazz = field.getType();
                    long timeout = field.getAnnotation(RpcResource.class).timeout();
                    field.set(bean, factory.getBean(clazz, timeout));
                    ClientCache.addService(clazz.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterStart() {
        if(ServerCache.isNotEmpty()) {
            ServerFactory.getInstance().start();
        }
        RegistryFactory.getInstance().start(hosts,factory.getScope());
        System.out.println("rpc was started !");
    }

    @Override
    public void destroy() {
        RegistryFactory.getInstance().destroy();
        ServerFactory.getInstance().destroy();
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public void setAppName(String name){
        System.setProperty("app-name",name);
    }

    public void setCache(String cache){
        System.setProperty("rpc-cache",cache);
    }

}
