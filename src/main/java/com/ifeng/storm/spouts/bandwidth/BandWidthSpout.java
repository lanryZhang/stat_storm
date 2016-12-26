package com.ifeng.storm.spouts.bandwidth;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class BandWidthSpout extends KafkaSpout {
    public BandWidthSpout() {
        super(new KafkaSpoutConfig("kafka.properties").getBandwidthTopicConfig());
    }
}