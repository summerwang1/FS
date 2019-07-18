package com.sar.fs.app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.sar.fs.base.FSBaseFragment;
import com.sar.fs.base.FSBaseOtto;

/**
 * @auth: sarWang
 * @date: 2019-07-04 16:14
 * @describe
 */

public abstract class FSFragment extends Fragment {
    public static final String TAG = FSBaseFragment.class.getSimpleName();

    public FSFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    public void initView() {
    }

    public void initData() {
    }

    public void onClick(View view) {
    }

    public void onEvent(FSBaseOtto event) {
    }
}

