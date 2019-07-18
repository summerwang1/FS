package com.sar.fs.app.logger;

import cn.finalteam.toolsfinal.logger.LogLevel;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:08
 * @describe
 */

public final class FSLogSettings {
    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private FSLoggerITool logTool;
    private FSLogLevel logLevel;

    public FSLogSettings() {
        this.logLevel = FSLogLevel.FULL;
    }

    public FSLogSettings hideThreadInfo() {
        this.showThreadInfo = false;
        return this;
    }

    public FSLogSettings logTool(FSLoggerITool logTool) {
        this.logTool = logTool;
        return this;
    }

    public int getMethodCount() {
        return this.methodCount;
    }

    /** @deprecated */
    @Deprecated
    public FSLogSettings setMethodCount(int methodCount) {
        return this.methodCount(methodCount);
    }

    public FSLogSettings methodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }

        this.methodCount = methodCount;
        return this;
    }

    public boolean isShowThreadInfo() {
        return this.showThreadInfo;
    }

    public FSLogLevel getLogLevel() {
        return this.logLevel;
    }

    /** @deprecated */
    @Deprecated
    public FSLogSettings setLogLevel(FSLogLevel logLevel) {
        return this.logLevel(logLevel);
    }

    public FSLogSettings logLevel(FSLogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public int getMethodOffset() {
        return this.methodOffset;
    }

    /** @deprecated */
    @Deprecated
    public FSLogSettings setMethodOffset(int offset) {
        return this.methodOffset(offset);
    }

    public FSLogSettings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public FSLoggerITool getLogTool() {
        if (this.logTool == null) {
            this.logTool = new AndroidSysLogTool();
        }

        return this.logTool;
    }
}
