package com.ifeng.storm.spouts.comments;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by duanyb on 2016/12/23.
 */
public class CommentsSpouts extends KafkaSpout {
    public CommentsSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getCommentsTopicCofig());
    }
}
