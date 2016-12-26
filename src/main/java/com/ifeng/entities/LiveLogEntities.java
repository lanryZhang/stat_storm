package com.ifeng.entities;

import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveLogEntities implements IEncode, Serializable {
    private static final long serialVersionUID = -1653863053092104005L;
    private String addr;
    private String gateway;
    private String parent;
    private String role;
    private String memoryused;
    private String ifin;
    private String ifout;
    private String local_stream;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMemoryused() {
        return memoryused;
    }

    public void setMemoryused(String memoryused) {
        this.memoryused = memoryused;
    }

    public String getIfin() {
        return ifin;
    }

    public void setIfin(String ifin) {
        this.ifin = ifin;
    }

    public String getIfout() {
        return ifout;
    }

    public void setIfout(String ifout) {
        this.ifout = ifout;
    }

    public String getLocal_stream() {
        return local_stream;
    }

    public void setLocal_stream(String local_stream) {
        this.local_stream = local_stream;
    }


    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("addr",this.addr);
        document.put("gateway",this.gateway);
        document.put("parent",this.parent);
        document.put("role",this.role);
        document.put("memoryused",this.memoryused);
        document.put("ifin",this.ifin);
        document.put("ifout",this.ifout);
        document.put("local_stream",this.local_stream);
        document.put("createTime",this.createTime);
        return (T)document;
    }
    @Override
    public Object clone(){
        LiveLogEntities en = new LiveLogEntities();
        en.setAddr(this.getAddr());
        en.setGateway(this.getGateway());
        en.setParent(this.getParent());
        en.setRole(this.getRole());
        en.setMemoryused(this.getMemoryused());
        en.setIfin(this.getIfin());
        en.setIfout(this.getIfout());
        en.setLocal_stream(this.getLocal_stream());
        en.setCreateTime(this.getCreateTime());
        return en;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("addr:"+this.getAddr());
        stringBuffer.append("gateway:"+this.getGateway());
        stringBuffer.append("parent:"+this.getParent());
        stringBuffer.append("role:"+this.getRole());
        stringBuffer.append("memoryused:"+this.getMemoryused());
        stringBuffer.append("ifin:"+this.getIfin());
        stringBuffer.append("ifout:"+this.getIfout());
        stringBuffer.append("local_stream:"+this.getLocal_stream());
        stringBuffer.append("createTime:"+this.getCreateTime());
        return super.toString();
    }
}
