package com.ifeng.storm.spouts.vapp;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by duanyb on 2016/11/17.
 */
public class VappLogSpouts extends KafkaSpout {
    public VappLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getVAppTopicCofig());
    }
}
