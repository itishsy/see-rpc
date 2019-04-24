package com.see.rpc.registry;

public class ServerNode {

    private String token;

    private byte[] data;

    private String address;

    public ServerNode(String token,byte[] data,String address){
        this.token = token;
        this.data = data;
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public byte[] getData() {
        return data;
    }

    public String getAddress() {
        return address;
    }
}
