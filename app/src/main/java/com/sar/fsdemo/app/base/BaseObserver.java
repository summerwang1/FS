package com.sar.fsdemo.app.base;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;

import com.sar.fsdemo.business.mode.bean.CallBackVo;
import com.sar.fs.widget.progress_dialogs.ColaProgress;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @auth: sarWang
 * @date: 2019-07-09 18:54
 * @describe
 */
public abstract class BaseObserver<T> implements Observer<CallBackVo<T>> {

    protected Context mContext;
    private ColaProgress mProgressDialog;

    public BaseObserver(Context cxt) {
        this.mContext = cxt;
    }

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(CallBackVo<T> callBackVo) {
        onRequestEnd();
        if (callBackVo.isSuccess()) {
            try {
                onSuccess(callBackVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(callBackVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccess(CallBackVo<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(CallBackVo<T> t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
        this.mProgressDialog = ColaProgress.show(mContext, "Loading", true, false, null);
        ProgressDialog.show(mContext, "", "请稍后");
    }

    public void closeProgressDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }
}
