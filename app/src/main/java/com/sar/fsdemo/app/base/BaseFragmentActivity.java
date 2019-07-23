package com.sar.fsdemo.app.base;

import android.os.Bundle;
import android.view.MotionEvent;
import com.sar.fsdemo.app.MDMApp;
import com.sar.fsdemo.custom.CustomToast;
import com.sar.fs.base.FSBaseFragmentActivity;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;


@EActivity
public abstract class BaseFragmentActivity extends FSBaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDMApp.getInstance().addActivity(this);
        AppBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppBus.getInstance().unregister(this);
        MDMApp.getInstance().ActivityFinish(this);
    }

    @UiThread
    public void showDialog() {
        showDialog("Loading");
    }

    @UiThread
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @UiThread
    public void showCustomToast(int parser, int resId, String msg) {
        CustomToast.showToast(this, parser, resId, msg);
    }

    @UiThread
    @Override
    public void showToast(String msg) {
        CustomToast.showToast(this, msg);
    }

    @UiThread(delay = 800)
    public void showSoftInputDelay() {
        super.showSoftInput();
    }

    @UiThread(delay = 0)
    public void closeSoftInputDelay() {
        super.closeSoftInput();
    }
}
