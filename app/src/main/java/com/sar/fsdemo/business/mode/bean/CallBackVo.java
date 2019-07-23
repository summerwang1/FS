package com.sar.fsdemo.business.mode.bean;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:09
 * @describe  * 说明：统一回调实体
 * ｛"status":"0"成功，“1”失败,"message":"","resObj":obj｝
 */
public class CallBackVo<T> {

    private static int SUCCESS_CODE = 0;//成功的code
    private String msg;
    private int code;
    private T data;


    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
