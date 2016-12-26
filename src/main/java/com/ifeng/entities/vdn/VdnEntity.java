package com.ifeng.entities.vdn;

import com.ifeng.core.data.ILoader;
import com.ifeng.core.misc.IAdd;
import com.ifeng.core.data.IEncode;
import com.ifeng.mongo.MongoCodec;
import org.bson.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duanyb on 2016/10/14.
 */
public class VdnEntity extends MongoCodec implements IEncode,Serializable,IAdd {

    private String tr      = "";
    private String tm        = "";
    private String frm          = "";
    private String videotype = "";
    private String statistype = "";
    private String city         = "";
    private String netName   = "";
    private String chentype   = "";
    private String dateTime  = "";
    private Date delkey;
    private int firstFrame;
    private int requestSum;
    private int pausedOne;
    private int pausedTwo;
    private int pausedThree;
    private int pausedMore;
    private int flowFault;
    private int seekFault;
    private int imperfectFault;
    private int epgFail;
    private int otherflowFault;
    private int ipPortTimeout;
    private int ipsPortFail;
    private int AllSum;

    @Override
    public void decode(ILoader loader) {

    }
    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("tr",this.tr);
        document.put("tr",this.tr);
        document.put("frm",this.frm);
        document.put("videotype",this.videotype);
        document.put("statistype",this.statistype);
        document.put("city",this.city);
        document.put("netName",this.netName);
        document.put("chentype",this.chentype);
        document.put("dateTime",this.dateTime);
        document.put("delkey",this.delkey);
        document.put("firstFrame"   ,this.firstFrame);
        document.put("requestSum"  ,this.requestSum);
        document.put("pausedOne",this.pausedOne);
        document.put("pausedTwo",this.pausedTwo);
        document.put("pausedThree",this.pausedThree);
        document.put("pausedMore",this.pausedMore);
        document.put("flowFault",this.flowFault);
        document.put("seekFault",this.seekFault);
        document.put("imperfectFault",this.imperfectFault);
        document.put("epgFail",this.epgFail);
        document.put("otherflowFault",this.otherflowFault);
        document.put("ipPortTimeout",this.ipPortTimeout);
        document.put("ipsPortFail",this.ipsPortFail);
        document.put("AllSum",this.AllSum);




        return (T)document;
    }

    @Override
    public int hashCode() {
        return this.tm.hashCode() * 17
                + this.tr.hashCode() * 17
                + this.frm.hashCode() * 17
                + this.videotype.hashCode() * 17
                + this.city.hashCode() * 17
                + this.netName.hashCode() * 17
                + this.frm.hashCode() * 17
                + this.statistype.hashCode() * 17
                + this.dateTime.hashCode() * 17
                + this.chentype.hashCode() * 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof VdnEntity)) return false;
        VdnEntity en = (VdnEntity) obj;
        return en.tr.equals(this.tr) && en.videotype.equals(this.videotype)
                && en.city.equals(this.city) && en.netName.equals(this.netName)
                && en.chentype.equals(this.chentype) && en.statistype.equals(this.statistype)
                && en.frm.equals(this.frm) && en.tm.equals(this.tm)
                && en.dateTime.equals(this.dateTime);
    }


    public Date getDelkey() {
        return delkey;
    }

    public void setDelkey(Date delkey) {
        this.delkey = delkey;
    }

    @Override
    public void add(IAdd obj) {
        VdnEntity en = (VdnEntity)obj;
        this.firstFrame += en.firstFrame;
        this.requestSum += en.requestSum;
        this.pausedOne += en.pausedOne;
        this.pausedTwo += en.pausedTwo;
        this.pausedThree += en.pausedThree;
        this.pausedMore += en.pausedMore;
        this.flowFault += en.flowFault;
        this.seekFault += en.seekFault;
        this.imperfectFault += en.imperfectFault;
        this.epgFail += en.epgFail;
        this.otherflowFault += en.otherflowFault;
        this.ipPortTimeout += en.ipPortTimeout;
        this.ipsPortFail += en.ipsPortFail;
        this.AllSum += en.AllSum;

    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }

    public String getVideotype() {
        return videotype;
    }

    public void setVideotype(String videotype) {
        this.videotype = videotype;
    }

    public String getStatistype() {
        return statistype;
    }

    public void setStatistype(String statistype) {
        this.statistype = statistype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getChentype() {
        return chentype;
    }

    public void setChentype(String chentype) {
        this.chentype = chentype;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(int firstFrame) {
        this.firstFrame = firstFrame;
    }

    public int getRequestSum() {
        return requestSum;
    }

    public void setRequestSum(int requestSum) {
        this.requestSum = requestSum;
    }

    public int getPausedOne() {
        return pausedOne;
    }

    public void setPausedOne(int pausedOne) {
        this.pausedOne = pausedOne;
    }

    public int getPausedTwo() {
        return pausedTwo;
    }

    public void setPausedTwo(int pausedTwo) {
        this.pausedTwo = pausedTwo;
    }

    public int getPausedThree() {
        return pausedThree;
    }

    public void setPausedThree(int pausedThree) {
        this.pausedThree = pausedThree;
    }

    public int getPausedMore() {
        return pausedMore;
    }

    public void setPausedMore(int pausedMore) {
        this.pausedMore = pausedMore;
    }


    public int getFlowFault() {
        return flowFault;
    }

    public void setFlowFault(int flowFault) {
        this.flowFault = flowFault;
    }

    public int getSeekFault() {
        return seekFault;
    }

    public void setSeekFault(int seekFault) {
        this.seekFault = seekFault;
    }

    public int getImperfectFault() {
        return imperfectFault;
    }

    public void setImperfectFault(int imperfectFault) {
        this.imperfectFault = imperfectFault;
    }

    public int getEpgFail() {
        return epgFail;
    }

    public void setEpgFail(int epgFail) {
        this.epgFail = epgFail;
    }

    public int getOtherflowFault() {
        return otherflowFault;
    }

    public void setOtherflowFault(int otherflowFault) {
        this.otherflowFault = otherflowFault;
    }

    public int getIpPortTimeout() {
        return ipPortTimeout;
    }

    public void setIpPortTimeout(int ipPortTimeout) {
        this.ipPortTimeout = ipPortTimeout;
    }

    public int getIpsPortFail() {
        return ipsPortFail;
    }

    public void setIpsPortFail(int ipsPortFail) {
        this.ipsPortFail = ipsPortFail;
    }

    public int getAllSum() {
        return AllSum;
    }

    public void setAllSum(int allSum) {
        AllSum = allSum;
    }



}
