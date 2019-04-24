package com.see.rpc.cluster;

import com.see.rpc.cluster.loadbalance.PriorityRoundRobin;
import com.see.rpc.cluster.loadbalance.RoundRobin;
import com.see.rpc.cluster.loadbalance.WeightRoundRobin;

public enum LoadBalanceFactory {
    PriorityRoundRobin(){
        @Override
        public LoadBalance instance() {
            if(loadBalance == null)
                loadBalance = new PriorityRoundRobin(limit);
            return loadBalance;
        }
    },
    RoundRobin(){
        @Override
        public LoadBalance instance() {
            if(loadBalance == null)
                loadBalance = new RoundRobin(limit);
            return loadBalance;
        }
    },
    WeightRoundRobin(){
        @Override
        public LoadBalance instance() {
            if(loadBalance == null)
                loadBalance = new WeightRoundRobin(limit);
            return loadBalance;
        }
    };

    protected Limit limit = null;
    protected LoadBalance loadBalance = null;
    public LoadBalanceFactory limit(Limit limit){
        this.limit = limit;
        return this;
    }

    public abstract LoadBalance instance();

}
