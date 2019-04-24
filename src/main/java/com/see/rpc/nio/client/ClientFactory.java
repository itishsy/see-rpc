package com.see.rpc.nio.client;

import com.see.rpc.cache.ClientCache;
import com.see.rpc.nio.Client;
import com.see.rpc.nio.base.ServerInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClientFactory {

    private static class Singleton {
        private static final ClientFactory instance = new ClientFactory();
    }

    public static ClientFactory getInstance() {
        return Singleton.instance;
    }

    /**
     * 获取可用的client实例
     *
     * @param service
     * @return
     */
    public Set<Client> getClients(String service) {
        Set<Client> clients = new HashSet<>();
        Set<String> set = ClientCache.getServiceMap().get(service);
        Iterator<String> iterator = set.iterator();
        for(;iterator.hasNext();){
            String address = iterator.next();
            Client client = ClientCache.getClient(address);
            if(null == client){
                client = create(address);
            }
            clients.add(client);
        }
        return clients;
    }

    private synchronized Client create(String address){
        Client client = ClientCache.getClient(address);
        if(null == client) {
            client = new NettyClient(address);
            ClientCache.getClientMap().put(client, ServerInfo.NORMAL);
        }
        return client;
    }

    public void destroy(Client client){
        client.shutdown();
        ClientCache.getClientMap().remove(client);
    }
}
