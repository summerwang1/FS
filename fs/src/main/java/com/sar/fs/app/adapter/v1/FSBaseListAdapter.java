package com.sar.fs.app.adapter.v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:56
 * @describe
 */
public abstract class FSBaseListAdapter<T> extends BaseAdapter {
    protected Context listContext;
    protected List<T> listItems;
    protected LayoutInflater listItemsContainer;
    protected int listItemViewResource;

    public FSBaseListAdapter(Context mContext) {
        this.listContext = mContext;
        this.listItems = new ArrayList();
        this.listItemsContainer = LayoutInflater.from(mContext);
    }

    public FSBaseListAdapter(Context mContext, List<T> mList) {
        this.listContext = mContext;
        this.listItems = mList;
        this.listItemsContainer = LayoutInflater.from(mContext);
    }

    public FSBaseListAdapter(Context mContext, int mResource) {
        this.listContext = mContext;
        this.listItems = new ArrayList();
        this.listItemsContainer = LayoutInflater.from(mContext);
        this.listItemViewResource = mResource;
    }

    public FSBaseListAdapter(Context mContext, List<T> mList, int mResource) {
        this.listContext = mContext;
        this.listItems = mList;
        this.listItemsContainer = LayoutInflater.from(mContext);
        this.listItemViewResource = mResource;
    }

    public int getCount() {
        return this.listItems != null ? this.listItems.size() : 0;
    }

    public Object getItem(int position) {
        if (this.listItems == null) {
            return null;
        } else {
            return position > this.listItems.size() - 1 ? null : this.listItems.get(position);
        }
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        return null;
    }

    public List<T> getList() {
        return this.listItems;
    }

    public void setData(List<T> list) {
        if (list != null) {
            this.listItems = list;
            this.notifyDataSetChanged();
        }
    }

    public void add(T t) {
        if (t != null) {
            if (this.listItems == null) {
                this.listItems = new ArrayList();
            }

            this.listItems.add(t);
            this.notifyDataSetChanged();
        }
    }

    public void add(List<T> list) {
        if (list != null) {
            if (this.listItems == null) {
                this.listItems = list;
                this.notifyDataSetChanged();
            } else {
                this.listItems.addAll(list);
                this.notifyDataSetChanged();
            }
        }
    }

    public void addToTop(T t) {
        if (t != null) {
            if (this.listItems == null) {
                this.listItems = new ArrayList();
            }

            this.listItems.add(0, t);
            this.notifyDataSetChanged();
        }
    }

    public void addToTopList(List<T> list) {
        if (list != null) {
            if (this.listItems == null) {
                this.listItems = list;
                this.notifyDataSetChanged();
            } else {
                this.listItems.addAll(0, list);
                this.notifyDataSetChanged();
            }
        }
    }

    public void remove(int position) {
        this.listItems.remove(position);
        this.notifyDataSetChanged();
    }

    public void clear() {
        if (this.listItems != null) {
            this.listItems.clear();
        }

        this.notifyDataSetChanged();
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }
}
