//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sar.fs.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Process;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.sar.fs.app.cache.FSCache;
import com.sar.fs.app.exception.FSExceptionUncaught;
import com.sar.fs.app.fragment.FSFragmentActivity;
import com.sar.fs.app.image.FSImageLoader;
import com.sar.fs.app.image.FSImageLoaderImpl;
import com.sar.fs.app.logger.AndroidSysLogTool;
import com.sar.fs.app.logger.FSLog;
import com.sar.fs.app.logger.FSLogLevel;
import com.sar.fs.app.net.FSNetChangeObserver;
import com.sar.fs.app.net.FSNetWorkUtil;
import com.sar.fs.app.net.FSNetworkStateReceiver;
import com.sar.fs.app.preference.FSConfigImpl;
import com.sar.fs.app.preference.FSConfigToPreference;
import com.sar.fs.app.preference.FSConfigToProperties;
import com.sar.fs.config.FSGlobal;
import com.sar.fs.utils.FSAppUtil;
import com.sar.fs.utils.FSInstallation;
import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Headers.Builder;

public abstract class FSBaseApp extends Application {
    public static final String TAG = FSBaseApp.class.getSimpleName();
    private static FSBaseApp mApplication;
    private FSFragmentActivity mCurrentActivity;
    private FSCache mCache;
    private FSConfigImpl mConfig;
    private JobManager mJobManager;
    private FSImageLoaderImpl mImageLoader;
    private Boolean networkAvailable = false;
    private FSNetChangeObserver taNetChangeObserver;

    public FSBaseApp() {
    }

    public static FSBaseApp getFSApplication() {
        return mApplication;
    }

    public void onCreate() {
        super.onCreate();
        this.init();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTerminate() {
        super.onTerminate();
    }

    protected void onConnect(FSNetWorkUtil.netType type) {
        this.networkAvailable = true;
        if (this.mCurrentActivity != null) {
            this.mCurrentActivity.onConnect(type);
        }

    }

    protected void onDisConnect() {
        this.networkAvailable = false;
        if (this.mCurrentActivity != null) {
            this.mCurrentActivity.onDisConnect();
        }

    }

    private void init() {
        mApplication = this;
        this.initSystem();
        this.initSysConfig();
        this.getFSCache();
        this.getFSConfig();
        this.getFSImageLoader();
        this.getFSJobManager();
        if (FSGlobal.development) {
            FSExceptionUncaught.install(this);
        }

        this.taNetChangeObserver = new FSNetChangeObserver() {
            public void onConnect(FSNetWorkUtil.netType type) {
                super.onConnect(type);
                FSBaseApp.this.onConnect(type);
            }

            public void onDisConnect() {
                super.onDisConnect();
                FSBaseApp.this.onDisConnect();
            }
        };
        FSNetworkStateReceiver.registerObserver(this.taNetChangeObserver);
    }

    private void initSystem() {
        FSLog.init("super_logger").methodCount(1).logLevel(FSLogLevel.FULL).methodOffset(0).logTool(new AndroidSysLogTool());
        PackageInfo packageInfo = FSAppUtil.getPackageInfo(this.getApplicationContext());
        FSGlobal.mobileType = Build.MODEL;
        FSGlobal.osVersion = VERSION.RELEASE;
        FSGlobal.version = packageInfo.versionName;
        FSGlobal.versionCode = packageInfo.versionCode;
        Log.d(TAG, "device info: " + FSGlobal.mobileType + " / " + FSGlobal.osVersion + " / " + FSGlobal.version + " / " + FSGlobal.versionCode);
        TelephonyManager telephonyManager = FSAppUtil.getTelephonyManager(this.getApplicationContext());
        FSGlobal.android_id = Secure.getString(this.getContentResolver(), "android_id");
//        FSGlobal.device_id = telephonyManager.getDeviceId();
        FSGlobal.device_mac = FSNetWorkUtil.getMac();
        Log.d(TAG, "device id: " + FSGlobal.device_id);
        Log.d(TAG, "device mac: " + FSGlobal.device_mac);
        Log.d(TAG, "device android id: " + FSGlobal.android_id);
        FSGlobal.device_installation_id = FSInstallation.getID(this.getApplicationContext());
        Log.d(TAG, "device install id: " + FSGlobal.device_installation_id);
        DisplayMetrics metric = new DisplayMetrics();
        FSAppUtil.getWindowManager(this.getApplicationContext()).getDefaultDisplay().getMetrics(metric);
        FSGlobal.width = metric.widthPixels;
        FSGlobal.height = metric.heightPixels;
        FSGlobal.density = metric.density;
        Log.d(TAG, "device screen: " + FSGlobal.width + " * " + FSGlobal.height);
        Log.d(TAG, "device desity: " + FSGlobal.density);
    }

    private void initSysConfig() {
        Properties props = new Properties();
        InputStream in = FSConfigToProperties.class.getResourceAsStream(FSGlobal.assetsPath);

        try {
            if (in != null) {
                props.load(in);
                FSGlobal.cache_img = props.getProperty("cache_img", FSGlobal.cache_img);
                FSGlobal.cache_obj = props.getProperty("cache_obj", FSGlobal.cache_obj);
                FSGlobal.cache_file = props.getProperty("cache_file", FSGlobal.cache_file);
                FSGlobal.shared_preferences = props.getProperty("shared_preferences", FSGlobal.shared_preferences);
                FSGlobal.application_properties = props.getProperty("application_properties", FSGlobal.application_properties);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void initOkHttp() {
        List<Part> commomParams = new ArrayList();
        Headers commonHeaders = (new Builder()).build();
        List<Interceptor> interceptorList = new ArrayList();
        OkHttpFinalConfiguration.Builder builder = (new OkHttpFinalConfiguration.Builder()).setCommenParams(commomParams).setCommenHeaders(commonHeaders).setTimeout(10000L).setInterceptors(interceptorList).setDebug(FSGlobal.development);
        OkHttpFinal.getInstance().init(builder.build());
    }

    public FSCache getFSCache() {
        if (this.mCache == null) {
            this.mCache = FSCache.get(this.getApplicationContext());
        }

        return this.mCache;
    }

    public FSConfigImpl getFSConfig() {
        if (this.mConfig == null) {
            this.getFSPreferenceConfig();
        }

        return this.mConfig;
    }

    public FSConfigImpl getFSPropertiesConfig() {
        return this.getFSConfig(1);
    }

    public FSConfigImpl getFSPreferenceConfig() {
        return this.getFSConfig(0);
    }

    public FSConfigImpl getFSConfig(int confingType) {
        if (confingType == 0) {
            this.mConfig = FSConfigToPreference.getPreferenceConfig(this.getApplicationContext());
        } else if (confingType == 1) {
            this.mConfig = FSConfigToProperties.getPropertiesConfig(this.getApplicationContext());
        } else {
            this.mConfig = FSConfigToProperties.getPropertiesConfig(this.getApplicationContext());
        }

        if (!this.mConfig.isLoadConfig()) {
            this.mConfig.loadConfig();
        }

        return this.mConfig;
    }

    public JobManager getFSJobManager() {
        if (this.mJobManager == null) {
            Configuration configuration = (new Configuration.Builder(this)).minConsumerCount(1).consumerKeepAlive(120).build();
            this.mJobManager = new JobManager(configuration);
        }

        return this.mJobManager;
    }

    public FSImageLoaderImpl getFSImageLoader() {
        if (this.mImageLoader == null) {
            this.mImageLoader = FSImageLoader.getInstance();
        }

        return this.mImageLoader;
    }

    public void exitApp(Boolean isBackground) {
        if (!isBackground) {
            Process.killProcess(Process.myPid());
            System.exit(10);
        }

    }

    public FSFragmentActivity getFSCurrentActivity() {
        return this.mCurrentActivity;
    }

    public void setFSCurrentActivity(FSFragmentActivity activity) {
        this.mCurrentActivity = activity;
    }

    protected Boolean isNetworkAvailable() {
        return this.networkAvailable;
    }

    public void initImageDefault(int defaultLoadImage, int defaultEmptyImage, int defaultFailImage, int defaultAvatarImage) {
        FSGlobal.img_default_load = defaultLoadImage;
        FSGlobal.img_default_empty = defaultEmptyImage;
        FSGlobal.img_default_fail = defaultFailImage;
        FSGlobal.img_default_avatar = defaultAvatarImage;
    }
}
