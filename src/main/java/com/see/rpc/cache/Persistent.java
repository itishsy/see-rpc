package com.see.rpc.cache;

import com.see.rpc.config.PropertyConfiguration;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

import java.util.HashMap;

public class Persistent {
//
//    private static class Singleton{
//        private static final Persistent PERSISTENT = new Persistent();
//    }
//
//    public static Persistent getInstance(){
//        return Singleton.PERSISTENT;
//    }
//
//    private Cache cache;
//
//    private Persistent(){
//        CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
//        cache = cacheManager.getCache("rpcCache");
//    }
//
//    public void persistent(String key,Object value){
//        Element element = new Element(key, value);
//        cache.put(element);
//        cache.flush();
//    }
//
//    public Object get(String key) {
//        Element element = cache.get(key);
//        if (null != element)
//            return element.getObjectValue();
//        return null;
//    }
//
    public static void set0(String key,Object value){
        if(isCache()) {
            CacheManager cacheManager = newCacheManager();
            Cache cache = cacheManager.getCache("rpcCache");
            Element element = new Element(PropertyConfiguration.AppName + key, value);
            cache.put(element);
            cache.flush();
            cacheManager.shutdown();
        }
    }

    public static Object get0(String key){
        if (isCache()) {
            CacheManager cacheManager = newCacheManager();
            try {
                Cache cache = cacheManager.getCache("rpcCache");
                Element element = cache.get(PropertyConfiguration.AppName + key);
                if (null != element)
                    return element.getObjectValue();
            } finally {
                cacheManager.shutdown();
            }
        }
        return null;
    }

    private static CacheManager newCacheManager(){
        Configuration configuration = new Configuration();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("rpcCache");
        cacheConfiguration.setMaxElementsInMemory(1);
        cacheConfiguration.setEternal(true);
        cacheConfiguration.setOverflowToDisk(true);
        cacheConfiguration.setDiskPersistent(true);
        configuration.addCache(cacheConfiguration);
        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
        diskStoreConfiguration.setPath(".ehcache");
        configuration.addDiskStore(diskStoreConfiguration);
        return CacheManager.create(configuration);
    }

    private static boolean isCache(){
        return  "true".equals(System.getProperty("rpc-cache"));
    }
}
