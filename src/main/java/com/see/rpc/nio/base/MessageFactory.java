package com.see.rpc.nio.base;

import com.google.common.collect.Maps;
import com.see.rpc.common.serializer.SerializationUtil;

import java.util.Map;

public class MessageFactory {

    private static Map<String, ResponseFuture> futureMap = Maps.newConcurrentMap();

    public static boolean contains(String id){
        return futureMap.containsKey(id);
    }

    public static void set(Message message){
        Response  response = (Response) SerializationUtil.deserialize(message.getData());
        ResponseFuture future = futureMap.get(message.getId());
        future.set(response);
    }

    public static ResponseFuture newFuture(String id,long timeout){
        ResponseFuture future = new ResponseFuture(timeout);
        futureMap.put(id,future);
        return future;
    }

    public static void destroy(){
        futureMap.forEach((s, future) -> future.release());
        futureMap.clear();
    }

}
