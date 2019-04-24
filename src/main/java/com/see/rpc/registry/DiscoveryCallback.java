package com.see.rpc.registry;

import com.see.rpc.cache.ClientCache;
import com.see.rpc.cache.ServerCache;
import com.see.rpc.common.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiscoveryCallback implements WatchCallback {

    private final Set<String> services;

    public DiscoveryCallback(Set<String> services){
        this.services = services;
    }

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryCallback.class);

    @Override
    @SuppressWarnings("unchecked")
    public void onAdd(ServerNode serverNode) {
        String address = serverNode.getAddress();
        String token = serverNode.getToken();
        byte[] data = serverNode.getData();

        if(data == null || data.length ==0
                || address.isEmpty()
                || token.isEmpty()) return;

        if (token.equals(ServerCache.getToken())) return;

        String serviceStr = SecurityUtil.gunzip(new String(data));

        logger.info("address: " + address + ", token: "+ token + ", serviceStr: "+ serviceStr);

        Set<String> newSet = new HashSet<>(services);// Sets.newHashSet(services);
        newSet.retainAll(Arrays.asList(serviceStr.split(",")));

        logger.info("!newSet.isEmpty(): " + !newSet.isEmpty());
        if(!newSet.isEmpty()){
//            ClientCache.addUnServedToken(serverNode.getToken());
//        } else {
            ClientCache.addServiceAddress(newSet, address);
            logger.info("rpc service discovered. address:" + address);
            logger.info("rpc service discovered. services:" + newSet);
        }
    }

    @Override
    public void onRemove(ServerNode serverNode) {
        String address = serverNode.getAddress();
        if (address.isEmpty()) return;

        ClientCache.removeAddress(address);
        logger.info("rpc service abandoned. address:" + address);
    }
}
