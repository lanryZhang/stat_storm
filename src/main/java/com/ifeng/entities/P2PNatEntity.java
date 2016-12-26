package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;
import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by gutc on 2016/9/6.
 */
public class P2PNatEntity  implements IEncode, Serializable ,IAdd{
    private static final long serialVersionUID = 7698133386607180297L;
    private long unknownCount = 0L;
    private long nonOneCount = 0L;
    private long nonTwoCount = 0L;
    private long nonThreeCount = 0L;
    private long symmeCount = 0L;
    private long sendData = 0L;
    private long receiveBack = 0L;
    private long outTime = 0L;
    private String netName;
    private String date;
    private String time;
    private String dateTime;



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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public long getUnknownCount() {
        return unknownCount;
    }

    public void setUnknownCount(long unknownCount) {
        this.unknownCount = unknownCount;
    }

    public long getNonOneCount() {
        return nonOneCount;
    }

    public void setNonOneCount(long nonOneCount) {
        this.nonOneCount = nonOneCount;
    }

    public long getNonTwoCount() {
        return nonTwoCount;
    }

    public void setNonTwoCount(long nonTwoCount) {
        this.nonTwoCount = nonTwoCount;
    }

    public long getNonThreeCount() {
        return nonThreeCount;
    }

    public void setNonThreeCount(long nonThreeCount) {
        this.nonThreeCount = nonThreeCount;
    }

    public long getSymmeCount() {
        return symmeCount;
    }

    public void setSymmeCount(long symmeCount) {
        this.symmeCount = symmeCount;
    }

    public long getSendData() {
        return sendData;
    }

    public void setSendData(long sendData) {
        this.sendData = sendData;
    }

    public long getReceiveBack() {
        return receiveBack;
    }

    public void setReceiveBack(long receiveBack) {
        this.receiveBack = receiveBack;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }


    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("unknownCount",this.unknownCount);
        document.put("nonOneCount",this.nonOneCount);
        document.put("nonTwoCount",this.nonTwoCount);
        document.put("nonThreeCount",this.nonThreeCount);
        document.put("symmeCount",this.symmeCount);
        document.put("sendData",this.sendData);
        document.put("receiveBack",this.receiveBack);
        document.put("outTime",this.outTime);
        document.put("netName",this.netName);
        document.put("date",this.date);
        document.put("time",this.time);
        document.put("dateTime",this.dateTime);
        return (T)document;
    }

    @Override
    public void add(IAdd obj) {
        P2PNatEntity en = (P2PNatEntity)obj;
        this.unknownCount += en.getUnknownCount();
        this.nonOneCount += en.getNonOneCount();
        this.nonTwoCount += en.getNonTwoCount();
        this.nonThreeCount += en.getNonThreeCount();
        this.symmeCount += en.getSymmeCount();
        this.sendData += en.getSendData();
        this.receiveBack += en.getReceiveBack();
        this.outTime += en.getOutTime();
    }

    @Override
    public Object clone(){
        P2PNatEntity en = new P2PNatEntity();
        en.setUnknownCount(this.getUnknownCount());
        en.setNonOneCount(this.getNonOneCount());
        en.setNonTwoCount(this.getNonTwoCount());
        en.setNonThreeCount(this.getNonThreeCount());
        en.setSymmeCount(this.getSymmeCount());
        en.setSendData(this.getSendData());
        en.setReceiveBack(this.getReceiveBack());
        en.setOutTime(this.getOutTime());
        en.setDate(this.getDate());
        en.setTime(this.getTime());
        en.setDateTime(this.getDateTime());
        en.setNetName(this.getNetName());
        return en;
    }
}
