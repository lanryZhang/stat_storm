package com.ifeng.persistence;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AtomicDouble;
import com.ifeng.core.misc.DistinctAdd;
import com.ifeng.core.misc.IAdd;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhanglr on 2016/4/1.
 */
public class FlushHashMap<K, V> extends TriggerHashMap {
    protected static ConcurrentHashMap failMap;
    protected static Set distinctSet;

    protected IFlush flusher;

    private static final Logger logger = Logger.getLogger(FlushHashMap.class);
    public FlushHashMap() {
        this(20);
    }

    public FlushHashMap(int interval) {
        super(interval);
        if (failMap == null) {
            synchronized (FlushHashMap.class) {
                if (map == null) {
                    failMap = new ConcurrentHashMap<String, ConcurrentHashMap<K, V>>();
                    distinctSet = Sets.newSetFromMap(new ConcurrentHashMap<>());
                }
            }
        }
    }

    class FlushMapTask implements Runnable {
        @Override
        public void run() {
            doExecute();
        }
    }

    @Override
    protected void doExecute(){
        doFlush();
    }

    protected void doFlush() {
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<K, V>> mt;
            ConcurrentHashMap<String, ConcurrentHashMap<K, V>> nt;
            synchronized (map) {
                mt = map;
                nt = failMap;

                refreshContainer();
            }
            if (flusher != null && mt != null && mt.size() > 0) {
                flusher.flush(mt, nt);
            }

        } catch (Exception er) {
            logger.error(er);
        }
    }

    public ConcurrentHashMap<K, V> get(String key) {
        return (ConcurrentHashMap<K, V>) map.get(key);
    }

    public void putDistinct(String pkey, K key, V value, Object distKey) throws Exception {
        if (!distinctSet.contains(distKey)){
            distinctSet.add(distKey);
            put(pkey, key, value, false);
        } else {
            put(pkey, key, value, true);
        }
    }

    private void put(String pKey, K key, V value, Boolean isDuplicate) {
        ConcurrentHashMap<K, V> pm = (ConcurrentHashMap<K, V>) map.get(pKey);


        if (pm == null) {
            pm = new ConcurrentHashMap<>();
            map.put(pKey, pm);
        }

        if (pm.containsKey(key)) {

            if (value instanceof Integer) {
                if (!isDuplicate) {
                    synchronized (FlushHashMap.class){
                        pm.put(key, (V) Integer.valueOf((Integer) pm.get(key) + (Integer) value));
                    }
                }
            } else if (value instanceof Long) {
                if (!isDuplicate) {
                    synchronized (FlushHashMap.class){
                        pm.put(key, (V) Long.valueOf((Long) pm.get(key) + (Long) value));
                    }
                }
            } else if (value instanceof Double) {
                if (!isDuplicate) {
                    synchronized (FlushHashMap.class){
                        pm.put(key, (V) Double.valueOf((Double) pm.get(key) + (Double) value));
                    }
                }
            }else if (value instanceof AtomicInteger) {
                if (!isDuplicate) {
                    AtomicInteger ai = (AtomicInteger) pm.get(key);
                    ai.addAndGet((Integer) value);
                    pm.put(key, (V) ai);
                }
            } else if (value instanceof AtomicLong) {
                if (!isDuplicate) {
                    AtomicLong ai = (AtomicLong) pm.get(key);
                    ai.addAndGet((Long) value);
                    pm.put(key, (V) ai);
                }
            } else if (value instanceof AtomicDouble) {
                if (!isDuplicate) {
                    AtomicDouble ai = (AtomicDouble) pm.get(key);
                    ai.addAndGet((Double) value);
                    pm.put(key, (V) ai);
                }
            } else if (value instanceof DistinctAdd) {
                synchronized (FlushHashMap.class) {
                    DistinctAdd add = (DistinctAdd) pm.get(key);
                    if (isDuplicate) {
                        add.add1((DistinctAdd) value);
                    } else {
                        add.add((DistinctAdd) value);
                    }
                }
            } else if (value instanceof IAdd) {
                synchronized (FlushHashMap.class) {
                    if (!isDuplicate) {
                        IAdd add = (IAdd) pm.get(key);
                        add.add((IAdd) value);
                    }
                }
            }
        } else {
            pm.put(key, value);
        }
    }

    public void put(String pkey, K key, V value) {
        put(pkey, key, value, false);
    }

    public void setFlusher(IFlush flusher) {
        this.flusher = flusher;
    }

    protected void refreshContainer() {
        map = new ConcurrentHashMap<String, ConcurrentHashMap<K, V>>();
        failMap = new ConcurrentHashMap<String, ConcurrentHashMap<K, V>>();
        distinctSet = Sets.newSetFromMap(new ConcurrentHashMap<>());
    }
}