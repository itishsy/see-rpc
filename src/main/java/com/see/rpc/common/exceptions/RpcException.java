package com.see.rpc.common.exceptions;

/**
 * RPC异常
 */
public class RpcException extends RuntimeException {
    private static final long serialVersionUID = 1390696866045892051L;

    private String message;

    public RpcException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
