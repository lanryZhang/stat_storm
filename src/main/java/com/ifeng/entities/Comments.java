package com.ifeng.entities;

/**
 * Created by duanyb on 2016/11/17.
 */
public class Comments {

    private String  doc_url;
    private int  count;
    private int  join_count;
    private int  allcount;


    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getJoin_count() {
        return join_count;
    }

    public void setJoin_count(int join_count) {
        this.join_count = join_count;
    }

    public int getAllcount() {
        return allcount;
    }

    public void setAllcount(int allcount) {
        this.allcount = allcount;
    }
}
