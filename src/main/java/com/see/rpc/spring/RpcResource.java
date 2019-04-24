package com.see.rpc.spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Autowired(required = false)
public @interface RpcResource {

    String value() default "";

    /**
     * version argument
     *
     * @return
     */
    String version() default "1.0.release";

    /**
     * 等待返回最大失效时间
     *
     * @return
     */
    long timeout() default 0;
}
