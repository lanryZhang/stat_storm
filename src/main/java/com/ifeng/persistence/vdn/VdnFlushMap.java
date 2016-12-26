package com.ifeng.persistence.vdn;

import com.ifeng.core.misc.IAdd;
import com.ifeng.persistence.IFlush;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/10/14.
 */
public class VdnFlushMap<K, V> {
    private static int interval = 20;
    private static ConcurrentHashMap map;
    private IFlush flusher;
    private static ConcurrentHashMap isInputMap = new ConcurrentHashMap<String, Date>(2);


    public ConcurrentHashMap<K, V> get(String key) {
        return (ConcurrentHashMap<K, V>) map.get(key);
    }

    public void put(String pkey, K key, V value,String isInput) {
        ConcurrentHashMap<K, V> pm = (ConcurrentHashMap<K, V>) map.get(pkey);
        if (pm == null) {
            pm = new ConcurrentHashMap<K, V>();
            map.put(pkey, pm);
        }

        if (pm.containsKey(key)) {
            if (value instanceof Integer) {

                pm.put(key, (V) Integer.valueOf((Integer) pm.get(key) + (Integer) value));
            } else if (value instanceof Long) {
                pm.put(key, (V) Long.valueOf((Long) pm.get(key) + (Long) value));
            } else if (value instanceof Double) {
                pm.put(key, (V) Double.valueOf((Double) pm.get(key) + (Double) value));
            } else if (value instanceof IAdd) {
                IAdd add = (IAdd) pm.get(key);
                ((IAdd) value).add(add);
                pm.put(key, value);
            }
        } else {
            pm.put(key, value);
        }


        try {
            if(isInputMap.containsKey(isInput)){
                if(isInputMap.size()>=2){

                }
            }else{
                isInputMap.put(isInput,new Date());
                if(isInputMap.size()==2){
                    if( new Date().getTime() - ((Date)isInputMap.get(isInput)).getTime()>1000*10){


                    }

                }

            }
            ConcurrentHashMap<String, ConcurrentHashMap<K, V>> tmp = map;
            map = new ConcurrentHashMap<String, ConcurrentHashMap<K, V>>();
            if (flusher != null && tmp != null && tmp.size() > 0) {
                flusher.flush(tmp);
            }
        } catch (Exception er) {
        }
    }

    public void setFlusher(IFlush flusher) {
        this.flusher = flusher;
    }


}
