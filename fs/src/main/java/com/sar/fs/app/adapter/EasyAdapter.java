package com.sar.fs.app.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:07
 * @describe
 */
public class EasyAdapter<T> extends BaseEasyAdapter<T> {
    private List<T> mListItems;

    public EasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass, List<T> listItems) {
        super(context, itemViewHolderClass);
        this.setItems(listItems);
    }

    public EasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass) {
        super(context, itemViewHolderClass);
        this.mListItems = new ArrayList();
    }

    public EasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass, List<T> listItems, Object listener) {
        super(context, itemViewHolderClass, listener);
        this.setItems(listItems);
    }

    public EasyAdapter(Context context, Class<? extends EasyItemViewHolder> itemViewHolderClass, Object listener) {
        super(context, itemViewHolderClass, listener);
        this.mListItems = new ArrayList();
    }

    public int getCount() {
        return this.mListItems.size();
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public T getItem(int position) {
        return this.mListItems.get(position);
    }

    public List<T> getItems() {
        return this.mListItems;
    }

    public void setItems(List<T> listItems) {
        this.mListItems = listItems;
        this.notifyDataSetChanged();
    }

    public void setItemsWithoutNotifying(List<T> listItems) {
        this.mListItems = listItems;
    }

    public void addItem(T item) {
        this.mListItems.add(item);
        this.notifyDataSetChanged();
    }

    public boolean addItems(Collection<? extends T> items) {
        if (this.mListItems.addAll(items)) {
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItem(T item) {
        if (this.mListItems.remove(item)) {
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItems(Collection<? extends T> items) {
        if (this.mListItems.removeAll(items)) {
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        if (this.mListItems != null) {
            this.mListItems.clear();
        }

        this.notifyDataSetChanged();
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }
}
