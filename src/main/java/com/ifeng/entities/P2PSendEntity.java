package com.ifeng.entities;

import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/5/27.
 */
public class P2PSendEntity implements IEncode ,Serializable {
    private static final long serialVersionUID = 5606312554861698463L;
    private String inquiryStatus;
    private String trackServerInfo;
    private String initialStatus;
    private String guid;
    private String sendRes;
    private String sendData;
    private String sendSpeed;
    private String sendTime;
    private String supplyPeerStatus;
    private String sendTargetRewrite;
    private String date;
    private String time;
    private String dateTime;

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

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getSendRes() {
        return sendRes;
    }

    public void setSendRes(String sendRes) {
        this.sendRes = sendRes;
    }

    public String getSendSpeed() {
        return sendSpeed;
    }

    public void setSendSpeed(String sendSpeed) {
        this.sendSpeed = sendSpeed;
    }

    public String getSendTargetRewrite() {
        return sendTargetRewrite;
    }

    public void setSendTargetRewrite(String sendTargetRewrite) {
        this.sendTargetRewrite = sendTargetRewrite;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSupplyPeerStatus() {
        return supplyPeerStatus;
    }

    public void setSupplyPeerStatus(String supplyPeerStatus) {
        this.supplyPeerStatus = supplyPeerStatus;
    }

    public String getTrackServerInfo() {
        return trackServerInfo;
    }

    public void setTrackServerInfo(String trackServerInfo) {
        this.trackServerInfo = trackServerInfo;
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
        document.put("sendRes",this.sendRes);
        document.put("sendData",this.sendData);
        document.put("sendSpeed",this.sendSpeed);
        document.put("sendTime",this.sendTime);
        document.put("supplyPeerStatus",this.supplyPeerStatus);
        document.put("sendTargetRewrite",this.sendTargetRewrite);
        document.put("date",this.date);
        document.put("time",this.time);
        document.put("dateTime",this.dateTime);
        return  (T)document;
    }
}
