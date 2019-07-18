package com.sar.fs.app.logger;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:07
 * @describe
 */
public interface FSLoggerIPrinter {
    FSLoggerIPrinter t(String var1, int var2);

    FSLogSettings init(String var1);

    FSLogSettings getSettings();

    void d(String var1, Object... var2);

    void e(String var1, Object... var2);

    void e(Throwable var1, String var2, Object... var3);

    void w(String var1, Object... var2);

    void i(String var1, Object... var2);

    void v(String var1, Object... var2);

    void wtf(String var1, Object... var2);

    void json(String var1);

    void xml(String var1);

    void clear();
}

