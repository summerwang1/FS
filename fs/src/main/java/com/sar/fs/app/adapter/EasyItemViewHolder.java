package com.sar.fs.app.adapter;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:03
 * @describe
 */
import android.view.View;
import androidx.annotation.Nullable;

public abstract class EasyItemViewHolder<T> extends BaseEasyItmViewHolder {
    private T mItem;
    private Object mListener;

    public EasyItemViewHolder(View view) {
        super(view);
    }

    public EasyItemViewHolder(View view, T item) {
        super(view);
        this.setItem(item);
    }

    public T getItem() {
        return this.mItem;
    }

    public void setItem(T item) {
        this.mItem = item;
    }

    public abstract void initSetValues(T var1, EasyItemInfo var2);

    public void initSetListeners() {
    }

    @Nullable
    public <P> P getListener(Class<P> type) {
        return this.mListener != null ? type.cast(this.mListener) : null;
    }

    @Nullable
    public Object getListener() {
        return this.mListener;
    }

    protected void setListener(Object listener) {
        this.mListener = listener;
    }
}

