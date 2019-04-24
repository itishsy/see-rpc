package com.see.rpc.registry;

import com.see.rpc.cache.ClientCache;
import com.see.rpc.cache.ServerCache;
import com.see.rpc.common.util.SecurityUtil;
import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.registry.zk.ZkRegistry;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Created by Administrator on 2018/12/9.
 */
public class RegistryFactory {

    private Registry registry = null;

    private static class Singleton {
        private static final RegistryFactory instance = new RegistryFactory();
    }

    public static RegistryFactory getInstance() {
        return Singleton.instance;
    }

    public void start(String address, String scope) {
        if (registry == null) {
            registry = new ZkRegistry(address);
        }
        startRegister(scope);
        initDiscovery(scope);
    }


    private void startRegister(String scope) {
        Set<String> services = ServerCache.getServices();
        if (!services.isEmpty()) {

            //根据有序Set生成token
            String serviceString = StringUtils.deleteWhitespace(services.toString()) ;
            String token = SecurityUtil.md5(serviceString);
            String tokenNode = String.format("%s/%s/%s", Constants.ROOT, scope, token);

            if ("true".equals(System.getProperty("rpc-cache"))) {
                //token一样，不再注册token节点
                String lastToken = ServerCache.getToken();
                if (!token.equals(lastToken)) {
                    if (StringUtils.isBlank(lastToken))
                        registry.delete(String.format("%s/%s/%s", Constants.ROOT, scope, ServerCache.getToken()));

                    registry.persistent(tokenNode, SecurityUtil.gzip(serviceString.substring(1, serviceString.length() - 1)).getBytes());
                    ServerCache.setToken(token);
                }
            } else {
                registry.persistent(tokenNode, SecurityUtil.gzip(serviceString.substring(1, serviceString.length() - 1)).getBytes());
            }

            //注册address节点
            registry.ephemeral(String.format("%s/%s", tokenNode, ServerCache.address));
            System.out.println("rpc service registered. size:" + services.size() + ",address:" + ServerCache.address);
            System.out.println("registered services:" + serviceString);
        }
    }

    @SuppressWarnings("unchecked")
    private void initDiscovery(String scope) {
        Set<String> services = ClientCache.getServiceMap().keySet();
        if (!services.isEmpty()) {
            ClientCache.setToken(SecurityUtil.md5(services.toString()));
            String scopeNode = String.format("%s/%s", Constants.ROOT, scope);
            registry.watch(scopeNode, new DiscoveryCallback(services));
            if(!PropertyConfiguration.PROFILE.equals(scope)){
                registry.watch(String.format("%s/%s", Constants.ROOT, PropertyConfiguration.PROFILE), new DiscoveryCallback(services));
            }
        }
    }

    public void destroy() {
        if (registry != null) {
            registry.destroy();
        }
    }

}
