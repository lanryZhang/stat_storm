/*
* ThresholdHashMap.java 
* Created on  202016/12/24 12:57 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public abstract class TriggerHashMap {
    protected static ConcurrentHashMap map;

    protected static ScheduledThreadPoolExecutor tt = new ScheduledThreadPoolExecutor(20);

    private static final Logger logger = Logger.getLogger(TriggerHashMap.class);
    public TriggerHashMap() {
        this(20);
    }

    public TriggerHashMap(int interval) {
        if (map == null) {
            synchronized (TriggerHashMap.class) {
                if (map == null) {
                    tt.scheduleAtFixedRate(new TriggerHashMap.Task(), interval, interval, TimeUnit.SECONDS);
                    map = new ConcurrentHashMap<>();
                }
            }
        }
    }

    class Task implements Runnable {
        @Override
        public void run() {
            doExecute();
        }
    }

    protected abstract void doExecute();
}
