package com.sar.fs.base;

/**
 * @auth: sarWang
 * @date: 2019-07-05 14:55
 * @describe
 */
public abstract class FSBaseEntity<T> {
    public int status;
    public String message;
    public T result;

    public FSBaseEntity() {
    }
}
