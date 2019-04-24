package com.see.rpc.cluster.loadbalance;

import com.see.rpc.cluster.Limit;
import com.see.rpc.cluster.LoadBalanceFactory;
import com.see.rpc.cluster.LoadBalance;

import java.util.Set;
import java.util.stream.Collectors;

public class PriorityRoundRobin implements LoadBalance{

    private Limit limit;

    public PriorityRoundRobin(Limit limit){
        this.limit = limit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T select(Set<T> set) {
        int priority = 0;
        Set<T> result = null;
        while (result==null) {
            final int val = priority;
            result = set.stream().filter(c -> Integer.valueOf(limit.value(c) + "") == val).collect(Collectors.toSet());
            priority++;
        }
        if(result.isEmpty()) return null;
        if(result.size() == 1) return result.iterator().next();
        return LoadBalanceFactory.RoundRobin.instance().select(result);
    }
}
