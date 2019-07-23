package com.sar.fsdemo.app;

import android.app.Activity;

import com.sar.fs.base.FSBaseApp;

import java.util.LinkedList;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-09 11:30
 * @describe
 */
public class MDMApp extends FSBaseApp {

    private List<Activity> mList = new LinkedList<Activity>();

    public static MDMApp getInstance() {
        return (MDMApp) getFSApplication();
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public Activity getActivity() {
        return mList.get(mList.size() - 1);
    }

    public void ActivityFinish(Activity activity) {
        mList.remove(activity);
    }
}
