package com.sar.fs.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sar.fs.base.FSBaseApp;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-09 14:22
 * @describe
 */
public class FSPermissionUtil {
    // 请求单个权限
    public static void request_permission(Activity mActivity) {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // 最后的请求码是对应回调方法的请求码
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.CALL_PHONE}, 1001);
        } else {
            Toast.makeText(mActivity, "你已经有权限了，可以直接拨打电话", Toast.LENGTH_LONG).show();
        }
    }

    // 请求多个权限
    public static void request_permissions(Activity mActivity) {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(mActivity, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }
}
