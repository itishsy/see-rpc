package com.see.rpc.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by Administrator on 2018/12/31.
 */
@Configuration
@ConditionalOnProperty(prefix = "rpc", name = "registry-address")
public class RpcAutoConfiguration {

    @Value("${app.name}")
    private String appName;

    @Value("${rpc.registry-address}")
    private String regAddress;

    @Value("${rpc.cache:false}")
    private String cache;

    @Value("${rpc.scope:null}")
    private String scope;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public RpcBeanFactory rpcBeanFactory() {
        RpcBeanFactory factory = new RpcBeanFactory();
        factory.setScope("null".equals(scope) ? profile : scope);
        return factory;
    }

    @Bean
    public RpcBootstrap rpcBootstrap(){
        RpcBootstrap rpcBootstrap = new RpcBootstrap();
        rpcBootstrap.setHosts(regAddress);
        rpcBootstrap.setAppName(appName);
        rpcBootstrap.setCache(cache);
        return rpcBootstrap;
    }

}
