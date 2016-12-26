package com.ifeng.storm.spouts.ipserver;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class IpsLogSpouts extends KafkaSpout {
    public IpsLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getIpsTopicConfig());
    }
}
