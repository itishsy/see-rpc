package com.see.rpc.nio.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/8.
 */
public enum ServerInfo implements Serializable {

    NORMAL(0),
    STRESSED(1),
    BLOCKED(2),
    DOWN(3);

    private String address;
    private Integer value;

    ServerInfo(Integer value){
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getValue(){
        return value;
    }
}
