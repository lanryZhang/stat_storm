package com.ifeng.configuration;

import java.util.Properties;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class KafkaConsumerProperties extends  PropertiesConfig {
    public KafkaConsumerProperties(String path) {
        super(path);
    }

    @Override
    public Properties getProperties() {
        pro = super.getProperties();
        Properties consumerPro = new Properties();
        consumerPro.put("zookeeper.connect", pro.get("zookeeper.connect"));
        consumerPro.put("group.id", pro.get("group.id"));
        consumerPro.put("auto.commit.interval.ms", pro.get("auto.commit.interval.ms"));
        consumerPro.put("zookeeper.session.timeout.ms", pro.get("zookeeper.session.timeout.ms"));
        return consumerPro;
    }
}
