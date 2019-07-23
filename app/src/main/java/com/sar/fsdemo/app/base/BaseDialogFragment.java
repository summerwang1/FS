package com.sar.fsdemo.app.base;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.sar.fsdemo.R;
import com.sar.fsdemo.custom.CustomToast;


/**
 * @author Mr.Wang
 * @version v0.3.1
 * @time 2017/6/29
 * @describe
 */

public class BaseDialogFragment extends DialogFragment{

    BaseFragmentActivity baseFragmentActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomDialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        baseFragmentActivity = (BaseFragmentActivity) getActivity();
        return dialog;
    }

    public void showDialog() {
        baseFragmentActivity.showDialog("Loading");
    }

    public void dismissDialog() {
        baseFragmentActivity.dismissDialog();
    }

    public void showToast(int resId) {
        baseFragmentActivity.showToast(getString(resId));

    }

    public void showToast(String msg) {
        CustomToast.showToast(baseFragmentActivity, msg);
    }

}
