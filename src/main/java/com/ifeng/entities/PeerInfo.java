package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhanglr on 2016/5/31.
 */
public class PeerInfo implements Serializable,IAdd {
    private static final long serialVersionUID = -7055917643234897504L;
    private long p2p_DlBytes = 0L;
    private long cdn_DlBytes = 0L;
    private long cdnDoneCount = 0L;
    private long p2pDoneCount = 0L;
    private long p2pSendCount = 0L;
    private long p2pSendCountPre = 0L;

    public long getCdnDoneCount() {
        return cdnDoneCount;
    }

    public void setCdnDoneCount(long cdnDoneCount) {
        this.cdnDoneCount = cdnDoneCount;
    }

    public long getP2p_DlBytes() {
        return p2p_DlBytes;
    }

    public void setP2p_DlBytes(long p2p_DlBytes) {
        this.p2p_DlBytes = p2p_DlBytes;
    }

    public long getCdn_DlBytes() {
        return cdn_DlBytes;
    }

    public void setCdn_DlBytes(long cdn_DlBytes) {
        this.cdn_DlBytes = cdn_DlBytes;
    }

    public long getP2pDoneCount() {
        return p2pDoneCount;
    }

    public void setP2pDoneCount(long p2pDoneCount) {
        this.p2pDoneCount = p2pDoneCount;
    }

    public long getP2pSendCount() {
        return p2pSendCount;
    }

    public void setP2pSendCount(long p2pSendCount) {
        this.p2pSendCount = p2pSendCount;
    }

    public long getP2pSendCountPre() {
        return p2pSendCountPre;
    }


    public void setP2pSendCountPre(long p2pSendCountPre) {
        this.p2pSendCountPre = p2pSendCountPre;
    }
    @Override
    public void add(IAdd obj) {
        PeerInfo en = (PeerInfo)obj;
        this.p2p_DlBytes += en.getP2p_DlBytes();
        this.cdn_DlBytes += en.getCdn_DlBytes();
        this.cdnDoneCount += en.getCdnDoneCount();
        this.p2pDoneCount += en.getP2pDoneCount();
        this.p2pSendCount += en.getP2pSendCount();
        this.p2pSendCountPre += en.getP2pSendCountPre();
    }

    @Override
    public Object clone(){
        PeerInfo en = new PeerInfo();
        en.setCdn_DlBytes(this.getCdn_DlBytes());
        en.setCdnDoneCount(this.getCdnDoneCount());
        en.setP2p_DlBytes(this.getP2p_DlBytes());
        en.setP2pDoneCount(this.getP2pDoneCount());
        en.setP2pSendCount(this.getP2pSendCount());
        en.setP2pSendCountPre(this.getP2pSendCountPre());
        return en;
    }
}
