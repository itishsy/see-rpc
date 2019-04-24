package com.see.rpc.model;

/**
 * Created by Administrator on 2017/9/14.
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public @interface AnnoModelB {
    String value() default "";

    String name() default "";

    String[] arrays() default {};
    
    ExcelAAA nono() default ExcelAAA.NONONO;
    
    Class<? extends ExcelRule> rule() default ObjectAAA.class;
}
