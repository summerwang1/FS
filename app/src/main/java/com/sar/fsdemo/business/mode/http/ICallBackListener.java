package com.sar.fsdemo.business.mode.http;


import com.sar.fsdemo.business.mode.bean.CallBackVo;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:08
 * @describe
 */
public interface ICallBackListener {

    public void onSuccess(CallBackVo mCallBackVo);

    public void onFaild(CallBackVo mCallBackVo);

}
