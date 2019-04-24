package com.see.rpc.cluster;

public interface Limit<T,R> {
    R value(T obj);
}
