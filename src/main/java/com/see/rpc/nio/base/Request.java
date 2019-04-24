package com.see.rpc.nio.base;

public class Request {

    /**
     * 超时时间
     */
    private long timeout;
    /**
     * 请求的serviceName
     */
    private String service;

    /**
     * 请求的方法名称
     */
    private String method;
    /**
     * 请求的参数值
     */
    private Object[] args;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "request[" + service + ":" + method + "]";
    }
}
