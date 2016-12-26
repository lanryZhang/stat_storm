/*
* ThresholdHashMap.java 
* Created on  202016/12/24 13:12 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

import org.apache.storm.shade.com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ThresholdHashMap<K,V> extends TriggerHashMap implements IMap<K,V>{
    private Long threshod;
    private long expireTime;
    private ConcurrentHashMap<K,Long> expireSet = new ConcurrentHashMap<>();
    public ThresholdHashMap(Long threshlod){
        super(20);
        this.threshod = threshlod;
    }

    public ThresholdHashMap(Long threshlod,int interval){
        super(interval);
        this.expireTime = interval;
        this.threshod = threshlod;
    }

    @Override
    protected void doExecute() {
        ConcurrentHashMap<K,Long> temp = new ConcurrentHashMap<>() ;
        expireSet.forEach((k,v)->{
            if ((System.currentTimeMillis() - v) > expireTime * 1000){
                map.remove(k);
            }else{
                temp.put(k,v);
            }
        });
        expireSet.clear();
        expireSet = temp;
    }

    @Override
    public void put(K key, V value) {
        incr(key);
    }


    public boolean incr(K key) {
        if (map.containsKey(key)){
            AtomicLong v = (AtomicLong) map.get(key);
            if (v.get() >= threshod){
                return false;
            }else{
                v.incrementAndGet();
                map.put(key,v);
            }
        }else {
            map.put(key, new AtomicLong(1));
        }
        expireSet.put(key,System.currentTimeMillis());
        return true;
    }
}
