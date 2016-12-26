package com.ifeng.entities;

import com.ifeng.core.annotations.HBaseIncrement;
import com.ifeng.core.annotations.TypeFamily;
import com.ifeng.core.data.ILoader;
import com.ifeng.core.misc.IAdd;
import com.ifeng.hbase.HBaseCodec;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;

import java.io.Serializable;

/**
 * Created by duanyb on 2016/12/23.
 */
@TypeFamily(value = "commentsInfo")
public class CommentsInfo extends HBaseCodec implements Serializable,IAdd {
    private static final long serialVersionUID = -374089869684922261L;
    private String mediaId;
    @HBaseIncrement(true)
    private long comments;

    private String createDate;
    private String createTime;
    private String type;


    public CommentsInfo() {
        this.comments = 0L;
        this.mediaId = "";
        this.type = "";
    }


    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public int hashCode() {
        return this.createDate.hashCode() * 26
                + this.createTime.hashCode() * 25
                + this.mediaId.hashCode() * 26
                + this.type.hashCode() * 24
                + this.type.hashCode() * 22;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommentsInfo)) {
            return false;
        }
        CommentsInfo en = (CommentsInfo) obj;

        return en.createDate.equals(this.createDate)
                && en.createTime.equals(this.createTime)
                && en.mediaId.equals(this.mediaId)
                && en.type.equals(this.type);

    }

    @Override
    public Put encode() {
        return null;
    }

    @Override
    public void decode(ILoader loader) {

    }

    @Override
    public Increment incr() {

        return null;
    }

    @Override
    public Increment incr(String colName, long value) {
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public void add(IAdd obj) {
        CommentsInfo en = (CommentsInfo)obj;
        this.comments += en.getComments();
    }
}



