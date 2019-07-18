package com.sar.fs.app.preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:59
 * @describe
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FSField {
    String name() default "";

    String defaultValue() default "";
}
