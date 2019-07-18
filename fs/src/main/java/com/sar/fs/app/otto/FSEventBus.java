package com.sar.fs.app.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @auth: sarWang
 * @date: 2019-07-05 14:48
 * @describe
 */
public class FSEventBus extends Bus {
    private static Bus instance;

    public FSEventBus() {
    }

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus(ThreadEnforcer.ANY);
        }

        return instance;
    }
}
