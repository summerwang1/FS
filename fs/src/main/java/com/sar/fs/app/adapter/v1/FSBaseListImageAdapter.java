package com.sar.fs.app.adapter.v1;

import android.content.Context;

import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:57
 * @describe
 */
public abstract class FSBaseListImageAdapter<T> extends FSBaseListAdapter<T> {

    public FSBaseListImageAdapter(Context context) {
        super(context);

    }

    public FSBaseListImageAdapter(Context context, int resource) {
        super(context, resource);

    }

    public FSBaseListImageAdapter(Context context, List<T> data, int resource) {
        super(context, data, resource);

    }
}
