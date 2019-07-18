package com.sar.fs.app.adapter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:54
 * @describe
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutId {
    int value();
}
