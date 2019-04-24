package com.see.rpc.cache;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/12/8.
 */
public class ServerCache {

    public static String address;

    private static String token;

    //专门存放rpc bean，解藕spring容器
    private static Map<String, Object> services = new TreeMap<>();

    public static void put(String name,Object bean){
        services.put(name,bean);
    }

    public static Object getBean(String name){
        return services.get(name);
    }

    public static Set<String> getServices(){
        return services.keySet();
    }

    public static boolean isNotEmpty() {
        return !services.isEmpty();
    }

    public static String getToken() {
        if (StringUtils.isBlank(token)) {
            token = Persistent.get0("serverToken") + "";
        }
        return token;
    }

    public static void setToken(String token) {
        if(StringUtils.isBlank(token)) return;

        Persistent.set0("serverToken",token);
        ServerCache.token = token;
    }

    public static void clear(){
        address = null;
        services.clear();
    }

}
