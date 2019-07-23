package com.sar.fsdemo.app.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.sar.fsdemo.R;


/**
 * @author Mr.Wang
 * @version v0.3.1
 * @time 2017/6/29
 * @describe
 */

public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
    }
}
