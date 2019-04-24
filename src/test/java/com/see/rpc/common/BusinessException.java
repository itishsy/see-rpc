package com.see.rpc.common;

/**
 * 业务异常
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String messageCode, Object[] errorParam) {
        super(ExceptionTypes.BIZ_EXCEPTION, messageCode, errorParam);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String messageCode, String message) {
        super(messageCode, message);
    }
}
