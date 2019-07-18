package com.sar.fs.base;

/**
 * @auth: sarWang
 * @date: 2019-07-04 16:15
 * @describe
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.sar.fs.app.fragment.FSFragment;
import com.sar.fs.app.otto.FSEventBus;
import com.sar.fs.base.FSBaseDelegate.FSIFragment;
import com.sar.fs.widget.progress_dialogs.ColaProgress;
import com.squareup.otto.Bus;

public abstract class FSBaseFragment extends FSFragment implements FSIFragment {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + this.hashCode();
    protected static FSBaseApp mApp;
    protected ColaProgress mProgressDialog;
    protected Bus mEventBus;
    protected Context mContext;

    public FSBaseFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = FSBaseApp.getFSApplication();
        this.mEventBus = FSEventBus.getInstance();
        this.mEventBus.register(this);
        this.mContext = this.getContext();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mEventBus.unregister(this);
    }

    public Context getContext() {
        return this.getActivity();
    }

    public void showToast(String msg) {
        Snackbar.make(this.getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showDialog() {
        this.showDialog("请稍后");
    }

    public void showDialog(String msg) {
        this.mProgressDialog = ColaProgress.show(this.getActivity(), msg, true, false, null);
    }

    public void dismissDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }

    }

    public void gotoActivity(Class<?> cls) {
        this.gotoActivity(cls, (Bundle)null);
    }

    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), cls);
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
        }

        return str;
    }

    public String getHttpTaskKey() {
        return this.HTTP_TASK_KEY;
    }
}

