package com.sar.fs.app.exception;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import com.sar.fs.R.drawable;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @auth: sarWang
 * @date: 2019-07-04 18:57
 * @describe
 */

@SuppressLint({"NewApi"})
public final class FSExceptionUncaught {
    private static final String EXTRA_RESTART_ACTIVITY_CLASS = "com.sar.fs.app.exception.EXTRA_RESTART_ACTIVITY_CLASS";
    private static final String EXTRA_SHOW_ERROR_DETAILS = "com.sar.fs.app.exception.EXTRA_SHOW_ERROR_DETAILS";
    private static final String EXTRA_STACK_TRACE = "com.sar.fs.app.exception.EXTRA_STACK_TRACE";
    private static final String EXTRA_IMAGE_DRAWABLE_ID = "com.sar.fs.app.exception.EXTRA_IMAGE_DRAWABLE_ID";
    private static final String TAG = "FSExceptionUncaught";
    private static final String INTENT_ACTION_ERROR_ACTIVITY = "com.sar.fs.app.exception.ERROR";
    private static final String INTENT_ACTION_RESTART_ACTIVITY = "com.sar.fs.app.exception.RESTART";
    private static final String CAOC_HANDLER_PACKAGE_NAME = "com.sar.fs.app.exception";
    private static final String DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os";
    private static final int MAX_STACK_TRACE_SIZE = 131071;
    private static Application application;
    private static WeakReference<Activity> lastActivityCreated = new WeakReference(null);
    private static boolean isInBackground = false;
    private static boolean launchErrorActivityWhenInBackground = true;
    private static boolean showErrorDetails = true;
    private static boolean enableAppRestart = true;
    private static int defaultErrorActivityDrawableId;
    private static Class<? extends Activity> errorActivityClass;
    private static Class<? extends Activity> restartActivityClass;

    public FSExceptionUncaught() {
    }

    public static void install(Context context) {
        try {
            if (context == null) {
                Log.e("FSExceptionUncaught", "Install failed: context is null!");
            } else {
                if (Build.VERSION.SDK_INT < 14) {
                    Log.w("FSExceptionUncaught", "FSExceptionUncaught will be installed, but may not be reliable in API lower than 14");
                }

                UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
                if (oldHandler != null && oldHandler.getClass().getName().startsWith("com.sar.fs.app.exception")) {
                    Log.e("FSExceptionUncaught", "You have already installed FSExceptionUncaught, doing nothing!");
                } else {
                    if (oldHandler != null && !oldHandler.getClass().getName().startsWith("com.android.internal.os")) {
                        Log.e("FSExceptionUncaught", "IMPORTANT WARNING! You already have an UncaughtExceptionHandler, are you sure this is correct? If you use ACRA, Crashlytics or similar libraries, you must initialize them AFTER FSExceptionUncaught! Installing anyway, but your original handler will not be called.");
                    }

                    application = (Application)context.getApplicationContext();
                    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                        public void uncaughtException(Thread thread, Throwable throwable) {
                            Log.e("FSExceptionUncaught", "App has crashed, executing FSExceptionUncaught's UncaughtExceptionHandler", throwable);
                            if (FSExceptionUncaught.errorActivityClass == null) {
                                FSExceptionUncaught.errorActivityClass = FSExceptionUncaught.guessErrorActivityClass(FSExceptionUncaught.application);
                            }

                            if (FSExceptionUncaught.isStackTraceLikelyConflictive(throwable, FSExceptionUncaught.errorActivityClass)) {
                                Log.e("FSExceptionUncaught", "Your application class or your error activity have crashed, the custom activity will not be launched!");
                            } else if (FSExceptionUncaught.launchErrorActivityWhenInBackground || !FSExceptionUncaught.isInBackground) {
                                Intent intent = new Intent(FSExceptionUncaught.application, FSExceptionUncaught.errorActivityClass);
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                throwable.printStackTrace(pw);
                                String stackTraceString = sw.toString();
                                if (stackTraceString.length() > 131071) {
                                    String disclaimer = " [stack trace too large]";
                                    stackTraceString = stackTraceString.substring(0, 131071 - disclaimer.length()) + disclaimer;
                                }

                                if (FSExceptionUncaught.enableAppRestart && FSExceptionUncaught.restartActivityClass == null) {
                                    FSExceptionUncaught.restartActivityClass = FSExceptionUncaught.guessRestartActivityClass(FSExceptionUncaught.application);
                                } else if (!FSExceptionUncaught.enableAppRestart) {
                                    FSExceptionUncaught.restartActivityClass = null;
                                }

                                intent.putExtra("com.sar.fs.app.exception.EXTRA_STACK_TRACE", stackTraceString);
                                intent.putExtra("com.sar.fs.app.exception.EXTRA_RESTART_ACTIVITY_CLASS", FSExceptionUncaught.restartActivityClass);
                                intent.putExtra("com.sar.fs.app.exception.EXTRA_SHOW_ERROR_DETAILS", FSExceptionUncaught.showErrorDetails);
                                intent.putExtra("com.sar.fs.app.exception.EXTRA_IMAGE_DRAWABLE_ID", FSExceptionUncaught.defaultErrorActivityDrawableId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                FSExceptionUncaught.application.startActivity(intent);
                            }

                            Activity lastActivity = FSExceptionUncaught.lastActivityCreated.get();
                            if (lastActivity != null) {
                                lastActivity.finish();
                                FSExceptionUncaught.lastActivityCreated.clear();
                            }

                            FSExceptionUncaught.killCurrentProcess();
                        }
                    });
                    if (Build.VERSION.SDK_INT >= 14) {
                        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                            int currentlyStartedActivities = 0;

                            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                                if (activity.getClass() != FSExceptionUncaught.errorActivityClass) {
                                    FSExceptionUncaught.lastActivityCreated = new WeakReference(activity);
                                }

                            }

                            public void onActivityStarted(Activity activity) {
                                ++this.currentlyStartedActivities;
                                FSExceptionUncaught.isInBackground = this.currentlyStartedActivities == 0;
                            }

                            public void onActivityResumed(Activity activity) {
                            }

                            public void onActivityPaused(Activity activity) {
                            }

                            public void onActivityStopped(Activity activity) {
                                --this.currentlyStartedActivities;
                                FSExceptionUncaught.isInBackground = this.currentlyStartedActivities == 0;
                            }

                            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                            }

                            public void onActivityDestroyed(Activity activity) {
                            }
                        });
                    }

                    Log.i("FSExceptionUncaught", "FSExceptionUncaught has been installed.");
                }
            }
        } catch (Throwable var2) {
            Log.e("FSExceptionUncaught", "An unknown error occurred while installing FSExceptionUncaught, it may not have been properly initialized. Please report this as a bug if needed.", var2);
        }

    }

    private static boolean isStackTraceLikelyConflictive(Throwable throwable, Class<? extends Activity> activityClass) {
        do {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            StackTraceElement[] var3 = stackTrace;
            int var4 = stackTrace.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                StackTraceElement element = var3[var5];
                if (element.getClassName().equals("android.app.ActivityThread") && element.getMethodName().equals("handleBindApplication") || element.getClassName().equals(activityClass.getName())) {
                    return true;
                }
            }
        } while((throwable = throwable.getCause()) != null);

        return false;
    }

    private static Class<? extends Activity> guessRestartActivityClass(Context context) {
        Class<? extends Activity> resolvedActivityClass = getRestartActivityClassWithIntentFilter(context);
        if (resolvedActivityClass == null) {
            resolvedActivityClass = getLauncherActivity(context);
        }

        return resolvedActivityClass;
    }

    private static Class<? extends Activity> getRestartActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities((new Intent()).setAction("com.sar.fs.app.exception.RESTART"), PackageManager.GET_RESOLVED_FILTER);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo resolveInfo = resolveInfos.get(0);

            try {
                return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
            } catch (ClassNotFoundException var4) {
                Log.e("FSExceptionUncaught", "Failed when resolving the restart activity class via intent filter, stack trace follows!", var4);
            }
        }

        return null;
    }

    private static Class<? extends Activity> getLauncherActivity(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            try {
                return (Class<? extends Activity>) Class.forName(intent.getComponent().getClassName());
            } catch (ClassNotFoundException var3) {
                Log.e("FSExceptionUncaught", "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!", var3);
            }
        }

        return null;
    }

    private static Class<? extends Activity> guessErrorActivityClass(Context context) {
        Class<? extends Activity> resolvedActivityClass = getErrorActivityClassWithIntentFilter(context);
        if (resolvedActivityClass == null) {
            resolvedActivityClass = FSBaseErrorActivity.class;
        }

        return resolvedActivityClass;
    }

    private static Class<? extends Activity> getErrorActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities((new Intent()).setAction("com.sar.fs.app.exception.ERROR"), PackageManager.GET_RESOLVED_FILTER);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo resolveInfo = resolveInfos.get(0);

            try {
                return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
            } catch (ClassNotFoundException var4) {
                Log.e("FSExceptionUncaught", "Failed when resolving the error activity class via intent filter, stack trace follows!", var4);
            }
        }

        return null;
    }

    private static void killCurrentProcess() {
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    public static boolean isShowErrorDetailsFromIntent(Intent intent) {
        return intent.getBooleanExtra("com.sar.fs.app.exception.EXTRA_SHOW_ERROR_DETAILS", true);
    }

    public static int getDefaultErrorActivityDrawableIdFromIntent(Intent intent) {
        return intent.getIntExtra("com.sar.fs.app.exception.EXTRA_IMAGE_DRAWABLE_ID", drawable.crash_error_image);
    }

    public static String getAllErrorDetailsFromIntent(Context context, Intent intent) {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String buildDateAsString = getBuildDateAsString(context, dateFormat);
        String versionName = getVersionName(context);
        String errorDetails = "";
        errorDetails = errorDetails + "Build version: " + versionName + " \n";
        errorDetails = errorDetails + "Build date: " + buildDateAsString + " \n";
        errorDetails = errorDetails + "Current date: " + dateFormat.format(currentDate) + " \n";
        errorDetails = errorDetails + "Device: " + getDeviceModelName() + " \n\n";
        errorDetails = errorDetails + "Stack trace:  \n";
        errorDetails = errorDetails + getStackTraceFromIntent(intent);
        return errorDetails;
    }

    public static String getStackTraceFromIntent(Intent intent) {
        return intent.getStringExtra("com.sar.fs.app.exception.EXTRA_STACK_TRACE");
    }

    private static String getBuildDateAsString(Context context, DateFormat dateFormat) {
        String buildDate;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            ZipFile zf = new ZipFile(ai.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();
            buildDate = dateFormat.format(new Date(time));
            zf.close();
        } catch (Exception var8) {
            buildDate = "Unknown";
        }

        return buildDate;
    }

    private static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception var2) {
            return "Unknown";
        }
    }

    private static String getDeviceModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return model.startsWith(manufacturer) ? capitalize(model) : capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String s) {
        if (s != null && s.length() != 0) {
            char first = s.charAt(0);
            return Character.isUpperCase(first) ? s : Character.toUpperCase(first) + s.substring(1);
        } else {
            return "";
        }
    }

    public static Class<? extends Activity> getRestartActivityClassFromIntent(Intent intent) {
        Serializable serializedClass = intent.getSerializableExtra("com.sar.fs.app.exception.EXTRA_RESTART_ACTIVITY_CLASS");
        return serializedClass != null && serializedClass instanceof Class ? (Class)serializedClass : null;
    }

    public static void restartApplicationWithIntent(Activity activity, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.finish();
        activity.startActivity(intent);
        killCurrentProcess();
    }

    public static void closeApplication(Activity activity) {
        activity.finish();
        killCurrentProcess();
    }

    public static boolean isLaunchErrorActivityWhenInBackground() {
        return launchErrorActivityWhenInBackground;
    }

    public static void setLaunchErrorActivityWhenInBackground(boolean launchErrorActivityWhenInBackground) {
        launchErrorActivityWhenInBackground = launchErrorActivityWhenInBackground;
    }

    public static boolean isShowErrorDetails() {
        return showErrorDetails;
    }

    public static void setShowErrorDetails(boolean showErrorDetails) {
        showErrorDetails = showErrorDetails;
    }

    public static int getDefaultErrorActivityDrawable() {
        return defaultErrorActivityDrawableId;
    }

    public static void setDefaultErrorActivityDrawable(int defaultErrorActivityDrawableId) {
        defaultErrorActivityDrawableId = defaultErrorActivityDrawableId;
    }

    public static boolean isEnableAppRestart() {
        return enableAppRestart;
    }

    public static void setEnableAppRestart(boolean enableAppRestart) {
        enableAppRestart = enableAppRestart;
    }

    public static Class<? extends Activity> getErrorActivityClass() {
        return errorActivityClass;
    }

    public static void setErrorActivityClass(Class<? extends Activity> errorActivityClass) {
        errorActivityClass = errorActivityClass;
    }

    public static Class<? extends Activity> getRestartActivityClass() {
        return restartActivityClass;
    }

    public static void setRestartActivityClass(Class<? extends Activity> restartActivityClass) {
        restartActivityClass = restartActivityClass;
    }

    static {
        defaultErrorActivityDrawableId = drawable.crash_error_image;
        errorActivityClass = null;
        restartActivityClass = null;
    }
}
