package com.ifeng.storm.spouts.pagestat;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class WapNewsLogSpouts extends KafkaSpout {
    public WapNewsLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getWapNewsTopicCofig());
    }
}
