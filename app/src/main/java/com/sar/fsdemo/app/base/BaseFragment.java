package com.sar.fsdemo.app.base;


import com.sar.fsdemo.custom.CustomToast;
import com.sar.fs.base.FSBaseFragment;
import com.sar.fs.utils.FSAppUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;


/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2017.03.17
 * @describe
 */
@EFragment
public abstract class BaseFragment extends FSBaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPage;

    @UiThread
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @UiThread
    @Override
    public void showToast(String msg) {
        if (getActivity() != null){
            CustomToast.showToast(getActivity(), msg);
        }
    }

    @UiThread
    public void showCustomToast(int parser, int resId, String msg) {
        if (getActivity() != null) {
            CustomToast.showToast(getActivity(), parser, resId, msg);
        }
    }

    @UiThread
    public void showDialog() {
        showDialog("Loading");
    }

    @UiThread()
    public void closeSoftInput() {
        FSAppUtil.closeSoftInput(this.mContext);
    }

    @UiThread(delay = 500)
    public void closeSoftInputDelay() {
        FSAppUtil.closeSoftInput(this.mContext);
    }


}
