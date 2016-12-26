/*
* RedisConfigManage.java 
* Created on  202016/11/1 17:56 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.configuration;

import org.apache.log4j.Logger;
import java.util.*;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class RedisConfigManage extends PropertiesConfig {

    private Map<String, RedisConfig> redisConfigs;
    private static final Logger logger = Logger.getLogger(RedisConfigManage.class);
    private Properties pro;

    public RedisConfigManage(String path) {
        super(path);
    }

    public Map<String, RedisConfig> getRedisConfigs() {
        initRedis();
        return redisConfigs;
    }


    public void initRedis() {
        try {
            pro = getProperties();
            String[] instances = pro.getProperty("redis.instances").split(",");

            if (redisConfigs == null) {
                redisConfigs = new HashMap<>();
            }

            String instancePrefix = "redis.";
            for (String in : instances) {
                RedisConfig config = new RedisConfig();
                config.setDescription(pro.getProperty(instancePrefix + in + ".description"));
                config.setMaxIdle(Integer.valueOf(pro.getProperty(instancePrefix + in + ".maxIdle")));
                config.setMaxTotal(Integer.valueOf(pro.getProperty(instancePrefix + in + ".maxTotal")));
                config.setMaxWait(Integer.valueOf(pro.getProperty(instancePrefix + in + ".maxWait")));
                config.setMinEvictableIdleTimeMillis(Integer.valueOf(pro.getProperty(instancePrefix + in + ".minEvictableIdleTimeMillis")));
                config.setName(in);
                config.setPort(Integer.valueOf(pro.getProperty(instancePrefix + in + ".port")));
                config.setServerIp(pro.getProperty(instancePrefix + in + ".serverIp"));
                config.setShard(pro.getProperty(instancePrefix + in + ".sharded"));
                config.setNumTestsPerEvictionRun(Integer.valueOf(pro.getProperty(instancePrefix + in + ".numTestsPerEvictionRun")));
                config.setTestOnBorrow(Boolean.valueOf(pro.getProperty(instancePrefix + in + ".testOnBorrow")));
                config.setTestOnReturn(Boolean.valueOf(pro.getProperty(instancePrefix + in + ".testOnReturn")));
                config.setTestWhileIdle(Boolean.valueOf(pro.getProperty(instancePrefix + in + ".testWhileIdle")));
                config.setTimeBetweenEvictionRunsMillis(Integer.valueOf(pro.getProperty(instancePrefix + in + ".timeBetweenEvictionRunsMillis")));
                redisConfigs.put(in, config);
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

}
