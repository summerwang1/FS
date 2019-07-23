package com.sar.fsdemo.app.base;

/**
 *
 * @param <T>
 */
public class BaseEntity<T> {

    /**
     * 状态
     */
    public int status;

    /**
     * 提示
     */
    public String message;

    /**
     * 结果
     */
//    public T result;

    public BaseEntity() {
    }

    public BaseEntity(String message) {
        this.status = 1;
        this.message = message;
    }

    /**
     * 成功
     *
     * @return
     */
    public boolean Success() {
        return status == 0;
    }
}
