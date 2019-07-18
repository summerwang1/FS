package com.sar.fs.app.adapter;

import android.content.Context;
import android.view.View;

import com.sar.fs.app.adapter.annotations.FieldAnnotationParser;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:03
 * @describe
 */
public abstract class BaseEasyItmViewHolder {
    private View mView;

    public BaseEasyItmViewHolder(View view) {
        this.mView = view;
        FieldAnnotationParser.setViewFields(this, view);
    }

    public View getView() {
        return this.mView;
    }

    public Context getContext() {
        return this.mView.getContext();
    }
}