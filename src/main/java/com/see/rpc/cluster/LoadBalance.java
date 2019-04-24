package com.see.rpc.cluster;

import java.util.Collection;
import java.util.Set;

public interface LoadBalance {

    <T> T select(Set<T> set);
}
