package com.ifeng.entities.vdn;

import java.io.Serializable;

/**
 * Created by duanyb on 2016/10/18.
 */
public class VdnInfo implements Serializable {

    private String tr = "";
    private String tm = "";
    private String ip = "";
    private String uid = "";
    private String frm = "";
    private String url = "";
    private String err = "";
    private String ptype = "";
    private String guid = "";

    @Override
    public int hashCode() {
        return this.tm.hashCode() * 17
                + this.tr.hashCode() * 17
                + this.frm.hashCode() * 17
                + this.ip.hashCode() * 17
                + this.url.hashCode() * 17
                + this.err.hashCode() * 17
                + this.uid.hashCode() * 17
                + this.ptype.hashCode() * 17
                + this.guid.hashCode() * 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof VdnInfo)) return false;
        VdnInfo en = (VdnInfo) obj;
        return en.tr.equals(this.tr) && en.ptype.equals(this.ptype)
                && en.ip.equals(this.ip) && en.url.equals(this.url)
                && en.err.equals(this.err) && en.uid.equals(this.uid)
                && en.frm.equals(this.frm) && en.tm.equals(this.tm)
                && en.guid.equals(this.guid);
    }

    @Override
    public Object clone() {
        VdnInfo en = new VdnInfo();
        en.setIp(this.getIp());
        en.setUid(this.getUid());
        en.setFrm(this.getFrm());
        en.setErr(this.getErr());
        en.setUrl(this.getUrl());
        en.setPtype(this.getPtype());
        en.setGuid(this.getGuid());
        en.setTm("");
        en.setTr("");
        return en;
    }

    public Object clone_() {
        VdnInfo en = new VdnInfo();
        en.setIp(this.getIp());
        en.setUid(this.getUid());
        en.setFrm(this.getFrm());
        en.setUrl(this.getUrl());
        en.setPtype(this.getPtype());
        en.setGuid(this.getGuid());
        en.setTm(this.getTm());
        en.setTr(this.getTr());
        en.setErr("");
        return en;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
