package com.sar.fsdemo;

import android.view.View;

import com.sar.fsdemo.app.base.BaseFragmentActivity;
import com.sar.fsdemo.business.mode.bean.CallBackVo;
import com.sar.fsdemo.app.base.BaseObserver;
import com.sar.fsdemo.business.mode.http.Business;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {


    public void onClick(View view) {
        Business.getInstance().login(1, new BaseObserver() {
            @Override
            protected void onSuccess(CallBackVo t) throws Exception {

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }
        });
//        new Thread(()->
//                showToast("testest")
//        ).run();
    }
}
