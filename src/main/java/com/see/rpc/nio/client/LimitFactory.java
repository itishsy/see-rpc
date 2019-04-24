package com.see.rpc.nio.client;

import com.see.rpc.cache.ClientCache;
import com.see.rpc.cluster.Limit;
import com.see.rpc.nio.Client;
import com.see.rpc.nio.base.ServerInfo;

public enum LimitFactory implements Limit<Client,Object> {
    PriorityRoundRobin(){
        @Override
        public Integer value(Client client) {
            ServerInfo serverInfo = ClientCache.getClientMap().get(client);
            return serverInfo.getValue();
        }
    },
    WeightRoundRobin(){
        @Override
        public Object value(Client obj) {
            return null;
        }
    }
}
