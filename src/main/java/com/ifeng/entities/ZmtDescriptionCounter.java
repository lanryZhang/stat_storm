/*
* ZmtDescriptionCounter.java 
* Created on  202016/12/24 10:25 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.entities;

import com.ifeng.core.misc.IAdd;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ZmtDescriptionCounter implements Serializable ,IAdd{
    private Map<String,Integer> areas;
    private Map<String,Integer> cities;
    private Map<String,Integer> gender;

    public ZmtDescriptionCounter(){
        this.areas = new ConcurrentHashMap<>();
        this.cities = new ConcurrentHashMap<>();
        this.gender = new ConcurrentHashMap<>();
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

    @Override
    public void add(IAdd obj) {
        ZmtDescriptionCounter counter = (ZmtDescriptionCounter)obj;
        counter.getAreas().forEach((k,v)->{
            if (this.areas.containsKey(k)){
                this.areas.put(k, this.areas.get(k) + v);
            }else{
                this.areas.put(k, v);
            }
        });

        counter.getCities().forEach((k,v)->{
            if (this.cities.containsKey(k)){
                this.cities.put(k, this.cities.get(k) + v);
            }
            else{
                this.cities.put(k, v);
            }
        });

        counter.getGender().forEach((k,v)->{
            if (this.gender.containsKey(k)){
                this.gender.put(k, this.gender.get(k) + v);
            }
            else{
                this.gender.put(k, v);
            }
        });
    }
}
