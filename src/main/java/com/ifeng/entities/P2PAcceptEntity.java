package com.ifeng.entities;

import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/5/27.
 */
public class P2PAcceptEntity implements IEncode, Serializable {
    private static final long serialVersionUID = 2630874986384113104L;
    private String inquiryStatus;
    private String trackServerInfo;
    private String initialStatus;
    private String guid;
    private String availableRecvRes;
    private String knockDoor;
    private String recvSpeed;
    private String recvTime;
    private String demandPeerStatus;
    private int supplyNum;
    private String date;
    private String time;
    private String dateTime;

    public String getAvailableRecvRes() {
        return availableRecvRes;
    }

    public void setAvailableRecvRes(String availableRecvRes) {
        this.availableRecvRes = availableRecvRes;
    }

    public String getDemandPeerStatus() {
        return demandPeerStatus;
    }

    public void setDemandPeerStatus(String demandPeerStatus) {
        this.demandPeerStatus = demandPeerStatus;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInitialStatus() {
        return initialStatus;
    }

    public void setInitialStatus(String initialStatus) {
        this.initialStatus = initialStatus;
    }

    public String getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public String getRecvSpeed() {
        return recvSpeed;
    }

    public void setRecvSpeed(String recvSpeed) {
        this.recvSpeed = recvSpeed;
    }

    public String getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(String recvTime) {
        this.recvTime = recvTime;
    }

    public int getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(int supplyNum) {
        this.supplyNum = supplyNum;
    }

    public String getTrackServerInfo() {
        return trackServerInfo;
    }

    public void setTrackServerInfo(String trackServerInfo) {
        this.trackServerInfo = trackServerInfo;
    }

    public String getKnockDoor() {
        return knockDoor;
    }

    public void setKnockDoor(String knockDoor) {
        this.knockDoor = knockDoor;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("inquiryStatus",this.inquiryStatus);
        document.put("trackServerInfo",this.trackServerInfo);
        document.put("initialStatus",this.initialStatus);
        document.put("guid",this.guid);
        document.put("availableRecvRes",this.availableRecvRes);
        document.put("knockDoor",this.knockDoor);
        document.put("recvSpeed",this.recvSpeed);
        document.put("recvTime",this.recvTime);
        document.put("demandPeerStatus",this.demandPeerStatus);
        document.put("supplyNum",this.supplyNum);
        document.put("date",this.date);
        document.put("time",this.time);
        document.put("dateTime",this.dateTime);
        return (T)document;
    }
}
