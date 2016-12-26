package com.ifeng.entities;

import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by gutc on 2016/8/24.
 */
public class P2PServerCountEntity implements IEncode, Serializable {
    private static final long serialVersionUID = 3748729282708462971L;
    private long peerCount;
    private long programCount;
    private String netName;
    private String dateTime;
    private String date;
    private String time;

    public long getPeerCount() {
        return peerCount;
    }

    public void setPeerCount(long peerCount) {
        this.peerCount = peerCount;
    }

    public long getProgramCount() {
        return programCount;
    }

    public void setProgramCount(long programCount) {
        this.programCount = programCount;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
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
        document.put("peerCount",this.peerCount);
        document.put("programCount",this.programCount);
        document.put("netName",this.netName);
        document.put("dateTime",this.dateTime);
        document.put("date",this.date);
        document.put("time",this.time);
        return (T)document;
    }
}
