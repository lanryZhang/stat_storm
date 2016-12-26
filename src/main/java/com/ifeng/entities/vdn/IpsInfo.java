package com.ifeng.entities.vdn;

import java.io.Serializable;

/**
 * Created by duanyb on 2016/10/18.
 */
public class IpsInfo implements Serializable {

    private String guid = "";
    private String reduurl = "";
    private String tm = "";
    private String tr = "";

    @Override
    public int hashCode() {
        return this.tm.hashCode() * 17
                + this.tr.hashCode() * 17
                + this.guid.hashCode() * 17
                + this.reduurl.hashCode() * 17
                ;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof IpsInfo)) return false;
        IpsInfo en = (IpsInfo) obj;
        return en.tr.equals(this.tr)
                && en.reduurl.equals(this.reduurl)
                && en.tm.equals(this.tm)
                && en.guid.equals(this.guid);
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getReduurl() {
        return reduurl;
    }

    public void setReduurl(String reduurl) {
        this.reduurl = reduurl;
    }
}
