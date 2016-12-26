package com.ifeng.entities;

import com.ifeng.core.annotations.HBaseIncrement;
import com.ifeng.core.annotations.TypeFamily;
import com.ifeng.core.data.AbsDataLoader;
import com.ifeng.core.data.ILoader;
import com.ifeng.hbase.HBaseCodec;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/10/9.
 */

@TypeFamily(value = "pageStatInfo")
public class PageStatInfo extends HBaseCodec implements Serializable {
    private static final long serialVersionUID = -3740809825324946801L;

    private String url;

    @HBaseIncrement(true)
    private long pv;
    @HBaseIncrement(true)
    private long uv;
    @HBaseIncrement(true)
    private long vv;
    @HBaseIncrement(true)
    private long comments;

    private String createDate;
    private String createTime;
    private String uid;
    private String pgcId;
    private String mediaId;
    private String type;


    public PageStatInfo() {
        this.url = "";
        this.pv = 0L;
        this.uv = 0L;
        this.vv = 0L;
        this.comments = 0L;
        this.createTime = "";
        this.createDate = "";
        this.uid = "";
        this.pgcId = "";
        this.mediaId = "";
        this.rowKey = "";
        this.type = "";
    }


    public String getUid() {
        return uid;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getVv() {
        return vv;
    }

    public void setVv(long vv) {
        this.vv = vv;
    }

    public String getPgcId() {
        return pgcId;
    }

    public void setPgcId(String pgcId) {
        this.pgcId = pgcId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public int hashCode() {
        return
                this.createDate.hashCode() * 26
                        + this.createTime.hashCode() * 25
                        + this.pgcId.hashCode() * 24
                        + this.mediaId.hashCode() * 23
                        + this.rowKey.hashCode() * 22
                        + this.type.hashCode() * 22;
//                + this.statsType.hashCode() * 31
//                + this.refer.hashCode() * 30
//                + this.url.hashCode() * 29;
//                + this.deviceDesc.hashCode() * 28
//                + this.ci.hashCode() * 27
//                +this.uid.hashCode() * 24;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PageStatInfo)) {
            return false;
        }
        PageStatInfo en = (PageStatInfo) obj;

        return en.pgcId.equals(this.pgcId)
                && en.mediaId.equals(this.mediaId)
                && en.createDate.equals(this.createDate)
                && en.createTime.equals(this.createTime)
                && en.type.equals(this.type)
                && en.rowKey.equals(this.rowKey);
    }

    @Override
    public Put encode() {
        Put put = new Put(this.getRowKey().getBytes());
        put.addColumn("pageStatInfo".getBytes(), "pgcId".getBytes(), this.pgcId.getBytes());
        put.addColumn("pageStatInfo".getBytes(), "mediaId".getBytes(), this.mediaId.getBytes());
        put.addColumn("pageStatInfo".getBytes(), "createDate".getBytes(), this.createDate.getBytes());
        put.addColumn("pageStatInfo".getBytes(), "createTime".getBytes(), this.createTime.getBytes());

        return put;
    }

    @Override
    public void decode(ILoader loader) {
        AbsDataLoader ld = (AbsDataLoader) loader;
        url = ld.getString("pageStatInfo", "url");
        pv = Long.valueOf(ld.getString("pageStatInfo", "pv"));
        vv = Long.valueOf(ld.getString("pageStatInfo", "vv"));
        uv = Long.valueOf(ld.getString("pageStatInfo", "uv"));
        createTime = ld.getString("pageStatInfo", "createTime");
        createDate = ld.getString("pageStatInfo", "createDate");
        uid = ld.getString("pageStatInfo", "uid");
        rowKey = ld.getRowKey();
    }

    @Override
    public Increment incr() {
        Increment inc = new Increment(this.getRowKey().getBytes());
        if (this.pv > 0) {
            inc.addColumn("pageStatInfo".getBytes(), "pv".getBytes(), this.pv);
        }
        if (this.pv > 0) {
            inc.addColumn("pageStatInfo".getBytes(), "vv".getBytes(), this.vv);
        }
        if (this.pv > 0) {
            inc.addColumn("pageStatInfo".getBytes(), "uv".getBytes(), this.uv);
        }
        if (this.comments > 0) {
            inc.addColumn("pageStatInfo".getBytes(), "comments".getBytes(), this.comments);
        }
        return inc;
    }

    @Override
    public Increment incr(String colName, long value) {
        Increment inc = new Increment(this.getRowKey().getBytes());
        inc.addColumn("pageStatInfo".getBytes(), colName.getBytes(), value);
        return inc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
