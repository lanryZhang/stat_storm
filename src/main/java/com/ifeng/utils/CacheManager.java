package com.ifeng.utils;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/10/18.
 */
public class CacheManager {
    private static ConcurrentHashMap cacheMap = new ConcurrentHashMap();

    private CacheManager() {
        super();
    }


    /**
     * returns cache item from hashmap
     *
     * @param key
     * @return Cache
     */
    private static Cache getCache(Object key) {
        return (Cache) cacheMap.get(key);
    }


    private static Cache remove(Object key) {
        return (Cache) cacheMap.remove(key);
    }

    /**
     * Looks at the hashmap if a cache item exists or not
     *
     * @param key
     * @return Cache
     */
    private static boolean hasCache(Object key) {
        return cacheMap.containsKey(key);
    }

    /**
     * Invalidates all cache
     */
    public static void invalidateAll() {
        cacheMap.clear();
    }

    /**
     * Invalidates a single cache item
     *
     * @param key
     */
    public static void invalidate(Object key) {
        cacheMap.remove(key);
    }

    /**
     * Adds new item to cache hashmap
     *
     * @param key
     * @return Cache
     */
    public static void putCache(Object key, Cache object) {
        cacheMap.put(key, object);
    }

    private class ClearThread extends Thread {
        ClearThread() {
            setName("clear cache thread");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    removeCache();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void removeCache() {
        long l = 0l;
        if (cacheMap != null && cacheMap.size() > 0) {

            for (Object obj : cacheMap.keySet()) {
                if (cacheExpired((Cache) cacheMap.get(obj))) {
                    cacheMap.remove(obj);
                }
                if(l==0l){
                    l = ((Cache) cacheMap.get(obj)).getTimeOut();
                }
            }
            try {
                Thread.sleep(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads a cache item's content
     *
     * @param key
     * @return
     */
    public static Cache getContent(Object key) {
        if (hasCache(key)) {
            Cache cache = getCache(key);
            return cache;
        } else {
            return null;
        }
    }

    /**
     * @param key
     * @param content
     * @param ttl
     */
    public static void putContent(Object key, Object content, long ttl) {
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setValue(content);
        cache.setTimeOut(ttl + new Date().getTime());
        cache.setExpired(false);
        putCache(key, cache);
    }

    /**
     * @modelguid {172828D6-3AB2-46C4-96E2-E72B34264031}
     */
    private static boolean cacheExpired(Cache cache) {
        if (cache == null) {
            return false;
        }
        long milisNow = new Date().getTime();
        long milisExpire = cache.getTimeOut();
        if (milisExpire < 0) {                // Cache never expires
            return false;
        } else if (milisNow >= milisExpire) {
            return true;
        } else {
            return false;
        }
    }


}
