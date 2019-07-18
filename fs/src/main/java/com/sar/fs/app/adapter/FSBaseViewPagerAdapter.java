package com.sar.fs.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:02
 * @describe
 */
public abstract class FSBaseViewPagerAdapter extends PagerAdapter {
    protected Context mContext;
    protected List<View> mListViews = null;

    public FSBaseViewPagerAdapter(Context context, List<View> mListViews) {
        this.mContext = context;
        this.mListViews = mListViews;
    }

    public int getCount() {
        return this.mListViews != null ? this.mListViews.size() : 0;
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View v = this.mListViews.get(position);
        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.mListViews.get(position));
    }

    public int getItemPosition(Object object) {
        return -2;
    }
}

