package com.ifeng.storm.spouts.media;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by duanyb on 2016/11/18.
 */
public class MediaLogSpouts extends KafkaSpout {
    public MediaLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getMediaTopicCofig());
    }
}
