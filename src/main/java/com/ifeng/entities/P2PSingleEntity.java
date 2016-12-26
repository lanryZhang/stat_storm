package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;
import com.ifeng.core.data.IEncode;
import org.bson.Document;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gutc on 2016/8/5.
 */
public class P2PSingleEntity implements IEncode, Serializable ,IAdd {
    private static final long serialVersionUID = 8480207893427645167L;
    private String guid;
    private long pInitialStatus = 0L;
    private long dWantRequestSum = 0L;
    private long dWantResponseSum = 0L;
    private long dKnockDoorSum = 0L;
    private long dRecvFirstDataSum = 0L;
    private long sSharedRequestSum = 0L;
    private long sLetDataGoSum = 0L;
    private long sRecvAckSum = 0L;
    private long pInquiryConnectSum = 0L;
    private long pInquiryResponseStatus = 0L;
    private long pTsConnectSum = 0L;
    private long pTsResponseStatus = 0L;
    private long pTcpShakeSuccessSum = 0L;
    private long pTcpShakeFailSum = 0L;
    private String date;
    private String time;
    private String dateTime;
    private long dWantFrequestSum = 0L;
    private long dWantFresponseSum = 0L;
    private long dAskResWithoutP2p = 0L;
    private long sShareSum = 0L;
    private long plLcnt = 0L;
    private long plVcnt = 0L;
    private long p2pDlB = 0L;
    private long cdnDlB = 0L;
    private long dKdRecvFirstDataSum = 0L;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public long getpInitialStatus() {
        return pInitialStatus;
    }

    public void setpInitialStatus(long pInitialStatus) {
        this.pInitialStatus = pInitialStatus;
    }
    public long getdWantRequestSum() {
        return dWantRequestSum;
    }

    public void setdWantRequestSum(long dWantRequestSum) {
        this.dWantRequestSum = dWantRequestSum;
    }

    public long getdWantResponseSum() {
        return dWantResponseSum;
    }

    public void setdWantResponseSum(long dWantResponseSum) {
        this.dWantResponseSum = dWantResponseSum;
    }

    public long getdKnockDoorSum() {
        return dKnockDoorSum;
    }

    public void setdKnockDoorSum(long dKnockDoorSum) {
        this.dKnockDoorSum = dKnockDoorSum;
    }
    public long getdRecvFirstDataSum() {
        return dRecvFirstDataSum;
    }

    public void setdRecvFirstDataSum(long dRecvFirstDataSum) {
        this.dRecvFirstDataSum = dRecvFirstDataSum;
    }
    public long getsSharedRequestSum() {
        return sSharedRequestSum;
    }

    public void setsSharedRequestSum(long sSharedRequestSum) {
        this.sSharedRequestSum = sSharedRequestSum;
    }

    public long getsLetDataGoSum() {
        return sLetDataGoSum;
    }

    public void setsLetDataGoSum(long sLetDataGoSum) {
        this.sLetDataGoSum = sLetDataGoSum;
    }

    public long getsRecvAckSum() {
        return sRecvAckSum;
    }

    public void setsRecvAckSum(long sRecvAckSum) {
        this.sRecvAckSum = sRecvAckSum;
    }

    public long getpInquiryConnectSum() {
        return pInquiryConnectSum;
    }

    public void setpInquiryConnectSum(long pInquiryConnectSum) {
        this.pInquiryConnectSum = pInquiryConnectSum;
    }

    public long getpInquiryResponseStatus() {
        return pInquiryResponseStatus;
    }

    public void setpInquiryResponseStatus(long pInquiryResponseStatus) {
        this.pInquiryResponseStatus = pInquiryResponseStatus;
    }
    public long getpTsConnectSum() {
        return pTsConnectSum;
    }

    public void setpTsConnectSum(long pTsConnectSum) {
        this.pTsConnectSum = pTsConnectSum;
    }

    public long getpTsResponseStatus() {
        return pTsResponseStatus;
    }

    public void setpTsResponseStatus(long pTsResponseStatus) {
        this.pTsResponseStatus = pTsResponseStatus;
    }

    public long getpTcpShakeSuccessSum() {
        return pTcpShakeSuccessSum;
    }

    public void setpTcpShakeSuccessSum(long pTcpShakeSuccessSum) {
        this.pTcpShakeSuccessSum = pTcpShakeSuccessSum;
    }
    public long getpTcpShakeFailSum() {
        return pTcpShakeFailSum;
    }

    public void setpTcpShakeFailSum(long pTcpShakeFailSum) {
        this.pTcpShakeFailSum = pTcpShakeFailSum;
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

    public long getdWantFrequestSum() {
        return dWantFrequestSum;
    }

    public void setdWantFrequestSum(long dWantFrequestSum) {
        this.dWantFrequestSum = dWantFrequestSum;
    }
    public long getdWantFresponseSum() {
        return dWantFresponseSum;
    }

    public void setdWantFresponseSum(long dWantFresponseSum) {
        this.dWantFresponseSum = dWantFresponseSum;
    }

    public long getdAskResWithoutP2p() {
        return dAskResWithoutP2p;
    }

    public void setdAskResWithoutP2p(long dAskResWithoutP2p) {
        this.dAskResWithoutP2p = dAskResWithoutP2p;
    }
    public long getsShareSum() {
        return sShareSum;
    }

    public void setsShareSum(long sShareSum) {
        this.sShareSum = sShareSum;
    }

    public long getPlLcnt() {
        return plLcnt;
    }

    public void setPlLcnt(long plLcnt) {
        this.plLcnt = plLcnt;
    }

    public long getPlVcnt() {
        return plVcnt;
    }

    public void setPlVcnt(long plVcnt) {
        this.plVcnt = plVcnt;
    }
    public long getP2pDlB() {
        return p2pDlB;
    }

    public void setP2pDlB(long p2pDlB) {
        this.p2pDlB = p2pDlB;
    }
    public long getCdnDlB() {
        return cdnDlB;
    }

    public void setCdnDlB(long cdnDlB) {
        this.cdnDlB = cdnDlB;
    }

    public long getdKdRecvFirstDataSum() {
        return dKdRecvFirstDataSum;
    }

    public void setdKdRecvFirstDataSum(long dKdRecvFirstDataSum) {
        this.dKdRecvFirstDataSum = dKdRecvFirstDataSum;
    }
    @Override
    public <T> T encode() {
        Document document = new Document();
        document.put("guid",this.guid);
        document.put("pInitialStatus",this.pInitialStatus);
        document.put("dWantRequestSum",this.dWantRequestSum);
        document.put("dWantResponseSum",this.dWantResponseSum);
        document.put("dKnockDoorSum",this.dKnockDoorSum);
        document.put("dRecvFirstDataSum",this.dRecvFirstDataSum);
        document.put("sSharedRequestSum",this.sSharedRequestSum);
        document.put("sLetDataGoSum",this.sLetDataGoSum);
        document.put("sRecvAckSum",this.sRecvAckSum);
        document.put("pInquiryConnectSum",this.pInquiryConnectSum);
        document.put("pInquiryResponseStatus",this.pInquiryResponseStatus);
        document.put("pTsConnectSum",this.pTsConnectSum);
        document.put("pTsResponseStatus",this.pTsResponseStatus);
        document.put("pTcpShakeSuccessSum",this.pTcpShakeSuccessSum);
        document.put("pTcpShakeFailSum",this.pTcpShakeFailSum);
        document.put("date",this.date);
        document.put("time",this.time);
        document.put("dateTime",this.dateTime);
        document.put("dWantFrequestSum",this.dWantFrequestSum);
        document.put("dWantFresponseSum",this.dWantFresponseSum);
        document.put("dAskResWithoutP2p",this.dAskResWithoutP2p);
        document.put("sShareSum",this.sShareSum);
        document.put("plLcnt",this.plLcnt);
        document.put("plVcnt",this.plVcnt);
        document.put("p2pDlB",this.p2pDlB);
        document.put("cdnDlB",this.cdnDlB);
        document.put("dKdRecvFirstDataSum",this.dKdRecvFirstDataSum);
        return (T)document;
    }

    @Override
    public void add(IAdd obj) {
        P2PSingleEntity en = (P2PSingleEntity)obj;
        this.dKdRecvFirstDataSum += en.getdKdRecvFirstDataSum();
        this.dRecvFirstDataSum += en.getdRecvFirstDataSum();
        this.dKnockDoorSum += en.getdKnockDoorSum();
        this.dAskResWithoutP2p += en.getdAskResWithoutP2p();
    }
    @Override
    public Object clone(){
        P2PSingleEntity en = new P2PSingleEntity();
        en.setGuid(this.getGuid());
        en.setpInitialStatus(this.getpInitialStatus());
        en.setdWantRequestSum(this.getdWantRequestSum());
        en.setdWantResponseSum(this.getdWantResponseSum());
        en.setdKnockDoorSum(this.getdKnockDoorSum());
        en.setdRecvFirstDataSum(this.getdRecvFirstDataSum());
        en.setsSharedRequestSum(this.getsSharedRequestSum());
        en.setsLetDataGoSum(this.getsLetDataGoSum());
        en.setsRecvAckSum(this.getsRecvAckSum());
        en.setpInquiryConnectSum(this.getpInquiryConnectSum());
        en.setpInquiryResponseStatus(this.getpInquiryResponseStatus());
        en.setpTsConnectSum(this.getpTsConnectSum());
        en.setpTsResponseStatus(this.getpTsResponseStatus());
        en.setpTcpShakeSuccessSum(this.getpTcpShakeSuccessSum());
        en.setpTcpShakeFailSum(this.getpTcpShakeFailSum());
        en.setDate(this.getDate());
        en.setTime(this.getTime());
        en.setDateTime(this.getDateTime());
        en.setdWantFrequestSum(this.getdWantFrequestSum());
        en.setdWantFresponseSum(this.getdWantFresponseSum());
        en.setdAskResWithoutP2p(this.getdAskResWithoutP2p());
        en.setsShareSum(this.getsShareSum());
        en.setPlLcnt(this.getPlLcnt());
        en.setPlVcnt(this.getPlVcnt());
        en.setP2pDlB(this.getP2pDlB());
        en.setCdnDlB(this.getCdnDlB());
        en.setdKdRecvFirstDataSum(this.getdKdRecvFirstDataSum());
        return en;
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" guid: "+this.getGuid());
        stringBuffer.append(" dKnockDoorSum: "+this.getdKnockDoorSum());
        stringBuffer.append(" dRecvFirstDataSum: "+this.getdRecvFirstDataSum());
        stringBuffer.append(" dKdRecvFirstDataSum: "+this.getdKdRecvFirstDataSum());
        stringBuffer.append(" dAskResWithoutP2p: "+this.getdAskResWithoutP2p());
        return String.valueOf(stringBuffer);
    }
}
