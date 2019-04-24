package com.see.rpc.cluster.loadbalance;

import com.see.rpc.cluster.Limit;
import com.see.rpc.cluster.LoadBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin implements LoadBalance {


    private Limit limit;

    public RoundRobin(Limit limit){
        this.limit = limit;
    }

    private final AtomicInteger pos = new AtomicInteger();

    @Override
    public <T> T select(Set<T> set) {
        List<T> list = new ArrayList<>(set);
        synchronized (pos){
            if(pos.get() >= set.size()){
                pos.set(0);
            }
            System.out.println();
            return list.get(pos.getAndIncrement());
        }
    }
}
