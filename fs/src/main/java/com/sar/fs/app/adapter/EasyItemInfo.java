package com.sar.fs.app.adapter;

/**
 * @auth: sarWang
 * @date: 2019-07-05 18:03
 * @describe
 */
public class EasyItemInfo {
    private int mPosition;
    private boolean mFirst;
    private boolean mLast;

    public EasyItemInfo() {
    }

    public EasyItemInfo(int position, boolean first, boolean last) {
        this.setPosition(position);
        this.setFirst(first);
        this.setLast(last);
    }

    public int getPosition() {
        return this.mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public boolean isFirst() {
        return this.mFirst;
    }

    public void setFirst(boolean first) {
        this.mFirst = first;
    }

    public boolean isLast() {
        return this.mLast;
    }

    public void setLast(boolean last) {
        this.mLast = last;
    }
}
