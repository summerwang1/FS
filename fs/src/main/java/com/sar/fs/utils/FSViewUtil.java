package com.sar.fs.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.view.View.MeasureSpec;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:50
 * @describe
 */
public class FSViewUtil {
    public FSViewUtil() {
    }

    public static void measureView(View v) {
        LayoutParams p = v.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(-1, -2);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.AT_MOST);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        v.measure(childWidthSpec, childHeightSpec);
    }

    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup)parent).removeView(v);
        }

    }

    public static float dip2px(Context context, float dipValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return applyDimension(1, dipValue, mDisplayMetrics);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }

        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch(unit) {
            case 0:
                return value;
            case 1:
                return value * metrics.density;
            case 2:
                return value * metrics.scaledDensity;
            case 3:
                return value * metrics.xdpi * 0.013888889F;
            case 4:
                return value * metrics.xdpi;
            case 5:
                return value * metrics.xdpi * 0.03937008F;
            default:
                return 0.0F;
        }
    }

    public static float px2dip(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.density;
    }

    public static float sp2px(Context context, float spValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return applyDimension(2, spValue, mDisplayMetrics);
    }

    public static float px2sp(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.scaledDensity;
    }
}
