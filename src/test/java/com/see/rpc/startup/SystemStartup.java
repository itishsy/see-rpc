package com.see.rpc.startup;

import org.springframework.context.support.GenericXmlApplicationContext;

public class SystemStartup {

    public static void main(String[] args) {
        if(args!=null && args.length>0) {
            for (int i = 0; i < args.length; i = i + 2) {
                System.setProperty(args[i].replace("-", ""), args[i + 1]);
            }
        }
        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("classpath:spring-apps/spring-system.xml");
        applicationContext.refresh();
    }
}
