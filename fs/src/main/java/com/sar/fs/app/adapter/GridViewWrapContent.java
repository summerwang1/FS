package com.sar.fs.app.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:09
 * @describe
 */
public class GridViewWrapContent extends GridView {
    public GridViewWrapContent(Context context) {
        super(context);
    }

    public GridViewWrapContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewWrapContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(536870911, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
