package com.see.rpc.common.exceptions;

/**
 * RPC异常
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1393999096045892051L;

    private String message;

    public ServiceException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
