package com.sar.fs.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:08
 * @describe
 */
public class GridViewPagerAdapter extends PagerAdapter {
    private List<GridView> mLists;

    public GridViewPagerAdapter(Context context, List<GridView> array) {
        this.mLists = array;
    }

    public int getCount() {
        return this.mLists.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public Object instantiateItem(View arg0, int arg1) {
        if ((this.mLists.get(arg1)).getParent() != null) {
            ((ViewGroup)(this.mLists.get(arg1)).getParent()).removeView(this.mLists.get(arg1));
        }

        ((ViewPager)arg0).addView(this.mLists.get(arg1));
        return this.mLists.get(arg1);
    }

    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager)arg0).removeView((View)arg2);
    }
}
