package com.see.rpc.nio.base;

import java.io.Serializable;

/**
 * 通道统一传输对象,提前序列化好业务数据
 *
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 8086813512939212001L;
    /**
     * 消息id
     */
    private String id;

    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 消息内容
     */
    private byte[] data;

    public Message(){}

    public Message(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
