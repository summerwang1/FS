package com.sar.fs.app.adapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:09
 * @describe
 */
public abstract class GridViewPagerDataAdapter<T> {
    public List listAll;
    public int rowInOnePage;
    public int columnInOnePage;

    public GridViewPagerDataAdapter(List<T> listAll, int rowInOnePage, int columnInOnePage) {
        this.listAll = listAll;
        this.rowInOnePage = rowInOnePage;
        this.columnInOnePage = columnInOnePage;
    }

    public abstract BaseAdapter getGridViewAdapter(List<T> var1, int var2);

    public abstract void onItemClick(AdapterView<?> var1, View var2, int var3, long var4, int var6);
}
