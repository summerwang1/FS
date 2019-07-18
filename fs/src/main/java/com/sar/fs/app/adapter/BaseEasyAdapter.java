package com.sar.fs.app.adapter;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:02
 * @describe
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sar.fs.app.adapter.annotations.ClassAnnotationParser;
import com.sar.fs.app.adapter.annotations.LayoutIdMissingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseEasyAdapter<T> extends BaseAdapter {
    private Class<? extends EasyItemViewHolder> mItemViewHolderClass;
    private LayoutInflater mInflater;
    private Integer mItemLayoutId;
    private Object mListener;

    public BaseEasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass) {
        this.init(context, itemViewHolderClass);
    }

    private void init(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass) {
        this.mItemViewHolderClass = itemViewHolderClass;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItemLayoutId = parseItemLayoutId(itemViewHolderClass);
    }

    private static Integer parseItemLayoutId(Class<? extends EasyItemViewHolder> itemViewHolderClass) {
        Integer itemLayoutId = ClassAnnotationParser.getLayoutId(itemViewHolderClass);
        if (itemLayoutId == null) {
            throw new LayoutIdMissingException();
        } else {
            return itemLayoutId;
        }
    }

    public BaseEasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass, Object listener) {
        this.init(context, itemViewHolderClass);
        this.mListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        EasyItemViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(this.mItemLayoutId, parent, false);
            holder = createViewHolder(convertView, this.mItemViewHolderClass);
            holder.setListener(this.mListener);
            holder.initSetListeners();
            if (convertView != null) {
                convertView.setTag(holder);
            }
        } else {
            holder = (EasyItemViewHolder)convertView.getTag();
        }

        T item = this.getItem(position);
        holder.setItem(item);
        EasyItemInfo positionInfo = new EasyItemInfo(position, position == 0, position == this.getCount() - 1);
        holder.initSetValues(item, positionInfo);
        return convertView;
    }

    private static EasyItemViewHolder createViewHolder(View view, Class<? extends EasyItemViewHolder> itemViewHolderClass) {
        try {
            Constructor<? extends EasyItemViewHolder> constructor = itemViewHolderClass.getConstructor(View.class);
            return (EasyItemViewHolder)constructor.newInstance(view);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        } catch (NoSuchMethodException var4) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " + itemViewHolderClass.getSimpleName(), var4);
        } catch (InvocationTargetException var5) {
            throw new RuntimeException(var5.getTargetException());
        } catch (InstantiationException var6) {
            throw new RuntimeException("Unable to instantiate " + itemViewHolderClass.getSimpleName(), var6);
        }
    }

    public abstract T getItem(int var1);
}

