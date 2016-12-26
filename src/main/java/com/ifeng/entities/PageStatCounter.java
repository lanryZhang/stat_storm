/*
* PageStatCounter.java 
* Created on  202016/11/1 10:00 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.entities;

import com.ifeng.core.misc.DistinctAdd;
import com.ifeng.core.misc.IAdd;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class PageStatCounter extends DistinctAdd implements Serializable {

    private static final long serialVersionUID = -137268828441815436L;
    private long pv =0L;
    private long uv = 0L;
    private long vv = 0L;
    private long comments;

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getVv() {
        return vv;
    }

    public void setVv(long vv) {
        this.vv = vv;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    @Override
    public void add(IAdd obj) {
        PageStatCounter en = (PageStatCounter)obj;
        this.pv += en.getPv();
        this.vv += en.getVv();
        this.uv += en.getUv();
    }

    @Override
    public void add1(IAdd obj) {
        PageStatCounter en = (PageStatCounter)obj;
        this.pv += en.getPv();
        this.vv += en.getVv();
    }
}
