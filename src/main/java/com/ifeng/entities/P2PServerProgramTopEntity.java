package com.ifeng.entities;

import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by gutc on 2016/8/24.
 */
public class P2PServerProgramTopEntity implements IEncode, Serializable {
    private static final long serialVersionUID = -7963203369231365055L;
    private String netName;
    private String dateTime;
    private long heat;
    private String url;
    private long topK;
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

    public long getHeat() {
        return heat;
    }

    public void setHeat(long heat) {
        this.heat = heat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTopK() {
        return topK;
    }

    public void setTopK(long topK) {
        this.topK = topK;
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
        document.put("heat",this.heat);
        document.put("url",this.url);
        document.put("topK",this.topK);
        document.put("date",this.date);
        document.put("time",this.time);
        return (T)document;
    }
}
