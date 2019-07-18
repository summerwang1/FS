package com.sar.fs.app.logger;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:06
 * @describe
 */

public final class FSLog {
    private static final String DEFAULT_TAG = "PRETTYLOGGER";
    private static FSLoggerIPrinter printer = new FSLoggerPrinter();

    private FSLog() {
    }

    public static FSLogSettings init() {
        return init("PRETTYLOGGER");
    }

    public static FSLogSettings init(String tag) {
        printer = new FSLoggerPrinter();
        return printer.init(tag);
    }

    public static void clear() {
        printer.clear();
        printer = null;
    }

    public static FSLoggerIPrinter t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static FSLoggerIPrinter t(int methodCount) {
        return printer.t((String)null, methodCount);
    }

    public static FSLoggerIPrinter t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void e(String message, Object... args) {
        printer.e((Throwable)null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    public static void json(String json) {
        printer.json(json);
    }

    public static void xml(String xml) {
        printer.xml(xml);
    }
}
