/*
* ExpireMap.java 
* Created on  202016/11/15 11:55 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ExpireMap<K,V> implements Serializable{

    private ConcurrentHashMap<K,V> map = new ConcurrentHashMap<>();
    private ConcurrentHashMap<K,Long> expreMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    public ExpireMap(){
        executorService.scheduleAtFixedRate(new ClearExpire(),1000,1000,TimeUnit.MILLISECONDS);
    }

    class ClearExpire implements Runnable{
        @Override
        public void run() {
            try {
                expreMap.forEach((k, v) -> {
                    if (System.currentTimeMillis() >= v) {
                        map.remove(k);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void put(K key,V value,Long expireAt){
        map.put(key,value);
        expreMap.put(key, System.currentTimeMillis() + expireAt);
    }

    public V get(K key){
        return map.get(key);
    }
}
