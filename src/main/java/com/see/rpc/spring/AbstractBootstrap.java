package com.see.rpc.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;

public abstract class AbstractBootstrap implements SmartLifecycle,BeanPostProcessor, DisposableBean {
    private boolean isRunning = false;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        callback.run();
        isRunning = false;
    }

    @Override
    public void start() {
        isRunning = true;
        afterStart();
    }

    @Override
    public void stop() {
        System.out.println("stop()!!!!!");
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return 0;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        setRpcResourceProxy(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        setRpcService(bean); // aop后
        return bean;
    }

    public abstract void setRpcService( Object bean);

    public abstract void setRpcResourceProxy(Object bean);

    public abstract void afterStart();
}
