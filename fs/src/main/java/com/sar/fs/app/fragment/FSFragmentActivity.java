package com.sar.fs.app.fragment;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sar.fs.app.net.FSNetWorkUtil;
import com.sar.fs.base.FSBaseOtto;
import com.sar.fs.utils.FSPermissionUtil;


public abstract class FSFragmentActivity extends AppCompatActivity {
    public static final String TAG = FSFragmentActivity.class.getSimpleName();

    public FSFragmentActivity() {
    }

    public void initMenuItem(MenuItem item) {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void onClick(View view) {
    }

    public void onEvent(FSBaseOtto event) {
    }

    public void onConnect(FSNetWorkUtil.netType type) {
        Log.d(TAG, String.format("%s %s", "onConnect", type));
    }

    public void onDisConnect() {
        Log.d(TAG, "onDisConnect");
    }

    @SuppressLint("RestrictedApi")
    public void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        Preconditions.checkNotNull(fragmentManager, "不能为空");
        Preconditions.checkNotNull(fragment, "不能为空");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
    protected void checkPermissions(){
        FSPermissionUtil.request_permissions(this);
    }
    // 请求权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                // 1001的请求码对应的是申请打电话的权限
                // 判断是否同意授权，PERMISSION_GRANTED 这个值代表的是已经获取了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "你同意授权，可以打电话了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "你不同意授权，不可以打电话", Toast.LENGTH_LONG).show();
                }
                break;
            case 1002:
                // 1002请求码对应的是申请多个权限
                if (grantResults.length > 0) {
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for (int i = 0; i < grantResults.length; i++) {
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(this, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}

