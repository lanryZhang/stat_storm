package com.ifeng.storm.spouts.pagestat;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class NewsAppLogSpouts extends KafkaSpout {
    public NewsAppLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getNewsAppTopicCofig());
    }
}
