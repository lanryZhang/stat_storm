package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;
import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class BandWidthEntity implements IEncode, Serializable ,IAdd {
    private String localTime ;
    private AtomicLong bodyBytesSent = new AtomicLong(0L);
    private String hostIp ;
    private String netName;
    private String type;
    private static final long serialVersionUID =  -12549156939504L;

/*    @Override
    public String toString(){
        return new StringBuilder(localTime)
               .append(" ").append(this.bodyBytesSent)
                .append(" ").append(hostIp)
                .append(" ").append(netName)
                .append(" ").append(type).toString();

    }
    @Override
    public int hashCode(){
        return this.localTime.hashCode() * 31
                + this.hostIp.hashCode() * 29
                + this.netName.hashCode() * 27
                + this.type.hashCode() * 25;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BandWidthEntity)) {
            return false;
        }
        BandWidthEntity en = (BandWidthEntity) obj;

        return this.localTime.equals(en.localTime)
                && this.hostIp.equals(en.hostIp)
                && this.netName.equals(en.netName)
                && this.type.equals(en.type);
    }*/

    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("localTime",this.localTime);
        document.put("netName", this.netName);
        document.put("type", this.type);
        document.put("bodyBytesSent", this.bodyBytesSent.get());
        document.put("hostIp", this.hostIp);
        return (T) document;
    }

    @Override
    public Object clone(){
        BandWidthEntity en = new BandWidthEntity();
        en.setNetName(this.getNetName());
        en.setType(this.getType());
        en.setHostIp(this.getHostIp());
        en.setLocalTime(this.getLocalTime());
        en.setBodyBytesSent(this.getBodyBytesSent());
        return en;
    }
    public AtomicLong getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(AtomicLong bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
    }
    public void setBodyBytesSent(long bodyBytesSent) {
        this.bodyBytesSent = new AtomicLong(bodyBytesSent);
    }
    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public void add(IAdd obj) {
        BandWidthEntity en = (BandWidthEntity)obj;
        this.bodyBytesSent.addAndGet(en.getBodyBytesSent().get());
    }
}
