package com.ifeng.storm.spouts.p2p;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class P2PLogSpouts extends KafkaSpout {
    public P2PLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getP2PTopicConfig());
    }
}
