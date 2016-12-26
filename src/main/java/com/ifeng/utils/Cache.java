package com.ifeng.utils;

/**
 * Created by duanyb on 2016/10/18.
 */
public class Cache<T> {
    private T key;
    private Object value;
    private boolean expired;
    private long timeOut;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public Cache() {

    }

    public Cache(T key, Object value, long timeOut, boolean expired) {
        this.key = key;
        this.value = value;
        this.expired = expired;
        this.timeOut = timeOut;
    }




}
