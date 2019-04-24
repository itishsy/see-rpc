package com.see.rpc.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Controller使用
 * 获取
 */
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String name){
        return ContextHolder.applicationContext.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextHolder.applicationContext = applicationContext;
    }
}
