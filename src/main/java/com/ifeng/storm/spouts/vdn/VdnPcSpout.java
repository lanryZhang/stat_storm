package com.ifeng.storm.spouts.vdn;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by duanyb on 2016/10/14.
 */
public class VdnPcSpout extends KafkaSpout {

    public VdnPcSpout() {
        super(new KafkaSpoutConfig("kafka.properties").getVdnPcConfig());
    }
}
