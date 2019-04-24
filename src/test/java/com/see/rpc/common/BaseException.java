package com.see.rpc.common;

/**
 * 异常基础类，迁移from spfcore
 */
public class BaseException extends Exception {

    private ExceptionTypes exceptionType;
    private String messageCode;
    private Object[] errorParam;

    public BaseException(ExceptionTypes exceptionType, String messageCode) {
        this(exceptionType,messageCode, null);

    }

    public BaseException(String message) {
        this((String)null, message);

    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String messageCode, String message) {
        super(message);
        this.messageCode = messageCode ;
    }

    public BaseException(ExceptionTypes exceptionType, String messageCode, Object[] errorParam) {
        super();
        this.exceptionType = exceptionType;
        this.messageCode = messageCode;
        this.errorParam = errorParam;
    }

    /*
     * Getter & Setter section
     */
    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getErrorParam() {
        return errorParam;
    }

    public void setErrorParam(Object[] errorParam) {
        this.errorParam = errorParam;
    }

    public ExceptionTypes getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionTypes exceptionType) {
        this.exceptionType = exceptionType;
    }

}
