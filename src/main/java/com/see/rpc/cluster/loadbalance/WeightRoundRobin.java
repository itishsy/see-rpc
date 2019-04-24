package com.see.rpc.cluster.loadbalance;

import com.see.rpc.cluster.Limit;
import com.see.rpc.cluster.LoadBalance;

import java.util.Set;

public class WeightRoundRobin implements LoadBalance {


    private Limit limit;

    public WeightRoundRobin(Limit limit){
        this.limit = limit;
    }

    @Override
    public <T> T select(Set<T> set) {
        return null;
    }
}
