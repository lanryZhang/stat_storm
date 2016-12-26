package com.ifeng.entities;

import com.ifeng.core.data.ILoader;
import com.ifeng.mongo.MongoCodec;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/5/27.
 */
public class PeerLogEntity extends MongoCodec implements Serializable{
    private static final long serialVersionUID = -2844592219749038257L;
    private String date = "";
    private String time = "";
    private String netName = "";
    private String groups = "";

    /**
     * 请求类型
     * 0 点播 1 手机直播
     */
    private String requestType = "";
    private String dateTime = "";

    //-------来自需求方参数----------//
    //-------Begin---------------

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }


//    public int getCdnDoneCount() {
//        return cdnDoneCount;
//    }
//
//    public void setCdnDoneCount(int cdnDoneCount) {
//        this.cdnDoneCount = cdnDoneCount;
//    }
//
//    public long getDlBytes() {
//        return dlBytes;
//    }
//
//    public void setDlBytes(long dlBytes) {
//        this.dlBytes = dlBytes;
//    }
//
//    public int getP2pDoneCount() {
//        return p2pDoneCount;
//    }
//
//    public void setP2pDoneCount(int p2pDoneCount) {
//        this.p2pDoneCount = p2pDoneCount;
//    }
//
//    public int getP2pSendCount() {
//        return p2pSendCount;
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public void setP2pSendCount(int p2pSendCount) {
//        this.p2pSendCount = p2pSendCount;
//    }
//
//    public int getP2pSendCountPre() {
//        return p2pSendCountPre;
//    }
//
//    public void setP2pSendCountPre(int p2pSendCountPre) {
//        this.p2pSendCountPre = p2pSendCountPre;
//    }


    @Override
    public void decode(ILoader loader) {

    }

    @Override
    public int hashCode(){
        return this.netName.hashCode() * 31
                + this.groups.hashCode() * 30
                + this.requestType.hashCode() * 29;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PeerLogEntity)) {
            return false;
        }
        PeerLogEntity en = (PeerLogEntity) obj;

        return this.netName.equals(en.netName)
                && this.groups.equals(en.groups)
                && this.requestType.equals(this.requestType);
    }
}
