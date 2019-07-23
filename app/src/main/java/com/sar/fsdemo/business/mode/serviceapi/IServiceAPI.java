package com.sar.fsdemo.business.mode.serviceapi;


import com.sar.fsdemo.business.mode.bean.CallBackVo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:15
 * @describe
 */
public interface IServiceAPI {

    //修改用户信息
    @FormUrlEncoded
    @POST("1005")
    Observable<CallBackVo<Object>> login(@Field("uid") int uid);


}