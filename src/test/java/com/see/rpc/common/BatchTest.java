package com.see.rpc.common;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BatchTest {

    private ExecutorService executorService;
    private List<Callable<Integer>> callables;
    private int size = 100;

    public BatchTest(List<Callable<Integer>> calls, int threads) {
        if (threads > 0)
            this.size = threads;
        executorService = Executors.newFixedThreadPool(size);
        this.callables = calls;
    }

    public BatchTest(List<Callable<Integer>> calls){
        this(calls,calls.size());
    }

    public void start(){
        try {
            List<Future<Integer>> futures = executorService.invokeAll(callables);
            int result = 0;
            for (Future<Integer> future : futures) {
                result += future.get();
            }
            System.out.println("并发执行完成。成功：" + result + "，失败：" + (size - result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
