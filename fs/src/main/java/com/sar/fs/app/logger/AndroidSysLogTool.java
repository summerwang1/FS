package com.sar.fs.app.logger;

import android.util.Log;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:12
 * @describe
 */
public class AndroidSysLogTool implements FSLoggerITool {
    public AndroidSysLogTool() {
    }

    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    public void e(String tag, String message) {
        Log.e(tag, message);
    }

    public void w(String tag, String message) {
        Log.w(tag, message);
    }

    public void i(String tag, String message) {
        Log.i(tag, message);
    }

    public void v(String tag, String message) {
        Log.v(tag, message);
    }

    public void wtf(String tag, String message) {
        Log.wtf(tag, message);
    }
}
