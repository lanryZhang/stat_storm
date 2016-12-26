/*
* ZmtDescription.java 
* Created on  202016/12/24 9:42 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ZmtDescription implements Serializable {
    private String createDate;
    private String createTime;
    private String pgcId;
    private Map<String,Integer> areas;
    private Map<String,Integer> cities;
    private Map<String,Integer> gender;

    public ZmtDescription(){
        this.createDate = "";
        this.createTime = "";
        this.pgcId = "";
        this.areas = new ConcurrentHashMap<>();
        this.cities = new ConcurrentHashMap<>();
        this.gender = new ConcurrentHashMap<>();
    }


    @Override
    public int hashCode() {
        return
                this.createDate.hashCode() * 26
                        + this.createTime.hashCode() * 25
                        + this.pgcId.hashCode() * 24;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ZmtDescription)) {
            return false;
        }
        ZmtDescription en = (ZmtDescription) obj;

        return en.pgcId.equals(this.pgcId)
                && en.createDate.equals(this.createDate)
                && en.createTime.equals(this.createTime);
    }

    public String getPgcId() {
        return pgcId;
    }

    public void setPgcId(String pgcId) {
        this.pgcId = pgcId;
    }

    public Map<String, Integer> getAreas() {
        return areas;
    }

    public void setAreas(Map<String, Integer> areas) {
        this.areas = areas;
    }

    public Map<String, Integer> getCities() {
        return cities;
    }

    public void setCities(Map<String, Integer> cities) {
        this.cities = cities;
    }

    public Map<String, Integer> getGender() {
        return gender;
    }

    public void setGender(Map<String, Integer> gender) {
        this.gender = gender;
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
}
