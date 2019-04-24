package com.see.rpc.nio.base;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResponseFuture {
    private volatile Response response = null;
    private Long timeout;
    private CountDownLatch latch = new CountDownLatch(1);



    public ResponseFuture(long timeout) {
        this.timeout = timeout;
    }

    public Response get() {
        try {
            latch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void set(Response response) {
        this.response = response;
        release();
    }

    public void release() {
        latch.countDown();
    }
}
