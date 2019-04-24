package com.see.rpc.nio.base;

public class Response {

    /**
     * Response Exception
     */
    private Throwable throwable;
    /**
     * 返回值
     */
    private Object result;

    public Response() {
    }

    public Response(Throwable throwable) {
        this.throwable = throwable;
    }


    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
