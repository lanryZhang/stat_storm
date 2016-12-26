package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;
import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gutc on 2016/8/25.
 */
public class P2PServerDurationEntity implements IEncode,Serializable,IAdd{
    private static final long serialVersionUID = 4440551389632338425L;
    private String netName;
    private String dateTime;
    private long userCount = 0L;
    private long duration = 0L;
    private String date;
    private String time;

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

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
        document.put("netName",this.netName);
        document.put("dateTime",this.dateTime);
        document.put("userCount",this.userCount);
        document.put("duration",this.duration);
        document.put("date",this.date);
        document.put("time",this.time);
        return (T) document;
    }

    @Override
    public void add(IAdd obj) {
        P2PServerDurationEntity en = (P2PServerDurationEntity)obj;
        this.userCount += en.getUserCount();
    }
}
