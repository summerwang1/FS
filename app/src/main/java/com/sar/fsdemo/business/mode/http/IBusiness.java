package com.sar.fsdemo.business.mode.http;

import com.sar.fsdemo.app.base.BaseObserver;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:07
 * @describe
 */
public interface IBusiness {

    //登录
    void login(int uid, BaseObserver baseObserver);

}