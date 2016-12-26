package com.ifeng.entities;

import com.ifeng.core.data.ILoader;
import com.ifeng.mongo.MongoCodec;
import org.bson.Document;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class IpsEntity extends MongoCodec implements Serializable {
    private String hostIp="";
    /**
     * 请求类型
     * 0 点播 1直播,2 手机直播
     */
    private String requestType = "";
    private String nodeIp = "";
    private String clientType = "";
    private int requestNum = 0;
    private String createDate = "";
    private String hm = "";
    private String dateTime = "";
   // private String isOver = "";

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHm() {
        return hm;
    }

    public void setHm(String hm) {
        this.hm = hm;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }


    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }


    public int getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

   /* public String getIsOver() {
        return isOver;
    }

    public void setIsOver(String isOver) {
        this.isOver = isOver;
    }*/

    @Override
    public String toString(){
        return new StringBuilder().append(this.hostIp).append(" ")
                .append(this.clientType).append(" ").append(this.createDate).append(" ")
                .append(this.hm).append(" ").append(this.getNodeIp()).append(" ").append(this.getRequestType()).toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IpsEntity)) {
            return false;
        }
        IpsEntity en = (IpsEntity) obj;
        return this.hostIp.equals(en.hostIp)
                && this.requestType.equals(en.requestType)
                && this.nodeIp.equals(en.nodeIp)
                && this.clientType.equals(en.clientType)
               // && this.isOver.equals(en.isOver)
                && this.createDate.equals(en.createDate)
                && this.hm.equals(en.hm);
    }

    @Override
    public int hashCode(){
       return this.requestType.hashCode() * 31
               + this.nodeIp.hashCode() * 29
               + this.clientType.hashCode() * 27
               + this.hostIp.hashCode() *25
               + this.hm.hashCode() * 23
               + this.createDate.hashCode()* 21;
              // + this.isOver.hashCode() *19;
    }

    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("hostIp",this.hostIp);
        document.put("requestType",this.requestType);
        document.put("nodeIp",this.nodeIp);
        document.put("clientType",this.clientType);
        document.put("requestNum",this.requestNum);
      //  document.put("isOver",this.isOver);
        document.put("createDate",this.createDate);
        document.put("hm",this.hm);
        document.put("dateTime",this.dateTime);
        return (T)document;
    }

    @Override
    public void decode(ILoader loader) {
    }

    @Override
    public IpsEntity clone(){
        IpsEntity clone = new IpsEntity();
        clone.setHostIp(this.hostIp);
        clone.setNodeIp(this.nodeIp);
        clone.setRequestNum(this.requestNum);
        clone.setRequestType(this.requestType);
        clone.setClientType(this.clientType);
        clone.setHm(this.hm);
        clone.setCreateDate(this.createDate);
        clone.setDateTime(this.dateTime);
        return clone;
    }
}
