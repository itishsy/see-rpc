package com.see.rpc.spring;

import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.nio.client.Proxy;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RpcBeanFactory {

    private String scope;

    private Map<String,Object> beanMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz){
        if(beanMap.containsKey(clazz.getName())){
            return (T)beanMap.get(clazz.getName());
        }
        return null;
    }

    protected Object getBean(Class<?> clazz,long timeout) {
        if (!beanMap.containsKey(clazz.getName())) {
            beanMap.put(clazz.getName(), Proxy.create(clazz)
                    .scope(scope)
                    .timeout(timeout > 0 ? timeout : PropertyConfiguration.RPC_TIMEOUT)
                    .build());
        }
        return beanMap.get(clazz.getName());
    }


    public String getScope() {
        if(StringUtils.isBlank(scope)){
            scope = PropertyConfiguration.PROFILE;
        }
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
