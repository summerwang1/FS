package com.sar.fs.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:20
 * @describe
 */

@SuppressLint({"SdCardPath"})
public class FSAppUtil {
    public FSAppUtil() {
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static Boolean callPhone(Context mContext, String num) {
        Uri callToPhone = Uri.parse("tel:");
        Intent mIntent = new Intent("android.intent.action.DIAL", callToPhone);
        mContext.startActivity(mIntent);
        return null;
    }

    public static Boolean sendSms(Context mContext, String smstext) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent mIntent = new Intent("android.intent.action.SENDTO", smsToUri);
        mIntent.putExtra("sms_body", smstext);
        mContext.startActivity(mIntent);
        return null;
    }

    public static Boolean sendMail(Context mContext, String title, String text) {
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.EMAIL", "");
        emailIntent.putExtra("android.intent.extra.SUBJECT", title);
        emailIntent.putExtra("android.intent.extra.TEXT", text);
        mContext.startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
        return null;
    }

    public static boolean isServiceRunning(Context ctx, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> servicesList = activityManager.getRunningServices(2147483647);
        Iterator l = servicesList.iterator();

        while(l.hasNext()) {
            RunningServiceInfo si = (RunningServiceInfo)l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    public static boolean stopRunningService(Context ctx, String className) {
        Intent intent_service = null;
        boolean ret = false;

        try {
            intent_service = new Intent(ctx, Class.forName(className));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        if (intent_service != null) {
            ret = ctx.stopService(intent_service);
        }

        return ret;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled("gps");
    }

    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, 2);
    }

    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity)context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 2);
        }

    }

    public static void getchmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;

        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace(System.err);
        }

        if (info == null) {
            info = new PackageInfo();
        }

        return info;
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.DELETE");
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            cachePath = context.getCacheDir().getPath();
        } else {
            cachePath = context.getExternalCacheDir().getPath();
        }

        return cachePath;
    }

    public static String getDiskFileDir(Context context, String fileName) {
        String cachePath = null;
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            cachePath = context.getFileStreamPath(fileName).getPath();
        } else {
            cachePath = context.getExternalFilesDir(fileName).getPath();
        }

        return cachePath;
    }

    @SuppressLint("MissingPermission")
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber = null;
        NativePhoneNumber = telephonyManager.getLine1Number();
        return NativePhoneNumber;
    }

    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        String IMSI = getIMSI(context);
        System.out.println(IMSI);
        if (!IMSI.startsWith("46000") && !IMSI.startsWith("46002")) {
            if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            } else {
                ProvidersName = "其他服务商";
            }
        } else {
            ProvidersName = "中国移动";
        }

        return ProvidersName;
    }

    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }
}
