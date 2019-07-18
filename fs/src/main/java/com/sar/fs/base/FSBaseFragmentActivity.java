package com.sar.fs.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import com.sar.fs.app.fragment.FSFragmentActivity;
import com.sar.fs.app.otto.FSEventBus;
import com.sar.fs.utils.FSAppUtil;
import com.sar.fs.widget.progress_dialogs.ColaProgress;
import com.squareup.otto.Bus;
import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * @auth: sarWang
 * @date: 2019-07-05 14:56
 * @describe
 */
public abstract class FSBaseFragmentActivity extends FSFragmentActivity implements FSBaseDelegate.FSIFragmentActivity {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + this.hashCode();
    protected static FSBaseApp mApp;
    protected ColaProgress mProgressDialog;
    protected Bus mEventBus;
    protected Context mContext;

    public FSBaseFragmentActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = FSBaseApp.getFSApplication();
        this.mEventBus = FSEventBus.getInstance();
        this.mEventBus.register(this);
        this.mContext = this.getContext();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mEventBus.unregister(this);
        HttpTaskHandler.getInstance().removeTask(this.HTTP_TASK_KEY);
    }

    public Context getContext() {
        return this;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 16908332:
                this.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initToolBar(Toolbar mToolBar) {
        if (mToolBar != null) {
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FSBaseFragmentActivity.this.onBackPressed();
                }
            });
            this.setSupportActionBar(mToolBar);
        }

    }

    public void showSoftInput() {
        FSAppUtil.showSoftInput(this.mContext);
    }

    public void closeSoftInput() {
        FSAppUtil.closeSoftInput(this.mContext);
    }

    public void onBackPressed() {
        if (this.getCurrentFocus() != null) {
            FSAppUtil.closeSoftInput(this);
        }

        super.onBackPressed();
    }

    public void showToast(String msg) {
        Snackbar.make(this.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showDialog() {
        this.showDialog("请稍后");
    }

    public void showDialog(String message) {
        this.mProgressDialog = ColaProgress.show(this, message, true, false, null);
    }

    public void dismissDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }

    }

    public void gotoActivity(Class<?> cls) {
        this.gotoActivity(cls, null);
    }

    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this.getBaseContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(intent);
    }

    public String getViewString(View view) {
        String str = null;
        if (view instanceof EditText) {
            str = ((EditText)view).getText().toString();
        } else if (view instanceof Button) {
            str = ((Button)view).getText().toString();
        } else if (view instanceof TextView) {
            str = ((TextView)view).getText().toString();
        }

        return str;
    }

    public String getHttpTaskKey() {
        return this.HTTP_TASK_KEY;
    }
}
