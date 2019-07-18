package com.sar.fs.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sar.fs.app.adapter.annotations.ClassAnnotationParser;
import com.sar.fs.app.adapter.annotations.LayoutIdMissingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:05
 * @describe
 */
public abstract class BaseEasyRecyclerAdapter<T> extends RecyclerView.Adapter<BaseEasyRecyclerAdapter.RecyclerViewHolder> {
    private Class mItemViewHolderClass;
    private LayoutInflater mInflater;
    private Integer mItemLayoutId;
    private Object mListener;

    public BaseEasyRecyclerAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass) {
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

    public BaseEasyRecyclerAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass, Object listener) {
        this.init(context, itemViewHolderClass);
        this.mListener = listener;
    }

    public BaseEasyRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = this.mInflater.inflate(this.mItemLayoutId, parent, false);
        EasyItemViewHolder<T> itemViewHolder = createViewHolder(itemView, this.mItemViewHolderClass);
        itemViewHolder.setListener(this.mListener);
        itemViewHolder.initSetListeners();
        return new BaseEasyRecyclerAdapter.RecyclerViewHolder(itemViewHolder);
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

    public void onBindViewHolder(BaseEasyRecyclerAdapter.RecyclerViewHolder recyclerViewHolder, int position) {
        T item = this.getItem(position);
        EasyItemViewHolder<T> itemViewHolder = recyclerViewHolder.itemViewHolder;
        EasyItemInfo positionInfo = new EasyItemInfo(position, position == 0, position == this.getItemCount() - 1);
        itemViewHolder.setItem(item);
        itemViewHolder.initSetValues(item, positionInfo);
    }

    public abstract T getItem(int var1);

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        EasyItemViewHolder itemViewHolder;

        public RecyclerViewHolder(EasyItemViewHolder itemViewHolder) {
            super(itemViewHolder.getView());
            this.itemViewHolder = itemViewHolder;
        }
    }
}
