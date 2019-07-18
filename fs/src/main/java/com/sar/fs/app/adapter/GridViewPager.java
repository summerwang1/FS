package com.sar.fs.app.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.viewpager.widget.ViewPager;

import com.sar.fs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:08
 * @describe
 */
public class GridViewPager extends ViewPager {
    private List<GridView> mLists = new ArrayList();
    private GridViewPagerAdapter adapter;
    private List listAll;
    private int rowInOnePage;
    private int columnInOnePage;
    private GridViewPagerDataAdapter gridViewPagerDataAdapter;

    public GridViewPager(Context context) {
        super(context);
    }

    public GridViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewPagerDataAdapter getGridViewPagerDataAdapter() {
        return this.gridViewPagerDataAdapter;
    }

    public void setGridViewPagerDataAdapter(GridViewPagerDataAdapter gridViewPagerDataAdapter) {
        this.gridViewPagerDataAdapter = gridViewPagerDataAdapter;
        if (gridViewPagerDataAdapter.listAll != null && gridViewPagerDataAdapter.listAll.size() != 0) {
            this.listAll = gridViewPagerDataAdapter.listAll;
            this.rowInOnePage = gridViewPagerDataAdapter.rowInOnePage;
            this.columnInOnePage = gridViewPagerDataAdapter.columnInOnePage;
            this.init();
        }
    }

    public void init() {
        int sizeInOnePage = this.rowInOnePage * this.columnInOnePage;
        int pageCount = this.listAll.size() / sizeInOnePage;
        pageCount += this.listAll.size() % sizeInOnePage == 0 ? 0 : 1;
        if (this.mLists.size() > pageCount) {
            for(int i = this.mLists.size() - 1; i >= pageCount; --i) {
                this.mLists.remove(i);
            }
        }

        for(int i = 0; i < pageCount; ++i) {
            GridViewWrapContent gv;
            if (i < this.mLists.size()) {
                gv = (GridViewWrapContent)this.mLists.get(i);
            } else {
                gv = new GridViewWrapContent(this.getContext());
                gv.setGravity(17);
                gv.setClickable(true);
                gv.setFocusable(true);
                gv.setSelector(R.drawable.titlebar_bar_btn_bg_android);
                gv.setNumColumns(this.columnInOnePage);
                this.mLists.add(gv);
            }

            int end = Math.min((i + 1) * sizeInOnePage, this.listAll.size());
            gv.setAdapter(this.gridViewPagerDataAdapter.getGridViewAdapter(this.listAll.subList(i * sizeInOnePage, end), i));
            final int finalI = i;
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    GridViewPager.this.gridViewPagerDataAdapter.onItemClick(arg0, arg1, arg2, arg3, finalI);
                }
            });
        }

        this.adapter = new GridViewPagerAdapter(this.getContext(), this.mLists);
        this.setAdapter(this.adapter);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;

        for(int i = 0; this.mLists != null && i < this.mLists.size(); ++i) {
            View child = this.mLists.get(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + this.getPaddingBottom() + this.getPaddingTop(), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
