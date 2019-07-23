package com.sar.fsdemo.app.base;

import com.squareup.otto.Bus;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2017/3/24
 * @describe
 */

public class AppBus extends Bus {
    private static AppBus bus;

    public static AppBus getInstance() {
        if (bus == null) {
            bus = new AppBus();
        }
        return bus;
    }


}
