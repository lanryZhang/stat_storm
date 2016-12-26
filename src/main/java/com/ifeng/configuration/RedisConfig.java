/*
* RedisConfig.java 
* Created on  202016/11/1 16:42 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.configuration;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class RedisConfig {
    private String name;
    private int maxTotal;
    private int maxIdle;
    private int maxWait;
    private boolean testOnBorrow;
    private String serverIp;
    private int port;
    private String shard;
    private String description;
    private boolean testOnReturn;
    private int timeBetweenEvictionRunsMillis;
    private int numTestsPerEvictionRun;
    private int minEvictableIdleTimeMillis;
    private boolean testWhileIdle;


    public int getMaxIdle() {
        return maxIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getPort() {
        return port;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShard() {
        return shard;
    }

    public void setShard(String shard) {
        this.shard = shard;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }


}

