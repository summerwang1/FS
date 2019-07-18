package com.sar.fs.app.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:01
 * @describe
 */
public abstract class FSBaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected FragmentManager mFragmentManager;
    protected List<Fragment> mFragmentList = null;

    public FSBaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmentManager = fm;
        this.mFragmentList = fragments;
    }

    public int getCount() {
        return this.mFragmentList != null ? this.mFragmentList.size() : 0;
    }

    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < this.mFragmentList.size()) {
            fragment = (Fragment)this.mFragmentList.get(position);
        } else {
            fragment = (Fragment)this.mFragmentList.get(0);
        }

        return fragment;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    public int getItemPosition(Object object) {
        return -2;
    }
}
