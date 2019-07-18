package com.sar.fs.base;

import android.os.Bundle;

import cn.finalteam.okhttpfinal.HttpCycleContext;

/**
 * @auth: sarWang
 * @date: 2019-07-04 16:16
 * @describe
 */
public interface FSBaseDelegate {

    public interface FSIPresenter {
        void init();
    }

    public interface FSIView<T> {
        void initPresenter(T var1);
    }

    public interface FSIFragment extends HttpCycleContext {
        void showToast(String var1);

        void showDialog();

        void showDialog(String var1);

        void dismissDialog();

        void gotoActivity(Class<?> var1);

        void gotoActivity(Class<?> var1, Bundle var2);
    }

    public interface FSIFragmentActivity extends FSBaseDelegate.FSIFragment {
        void showSoftInput();

        void closeSoftInput();
    }
}
