package com.ifeng.storm.spouts.live;
import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.storm.kafka.KafkaSpout;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveLogSpouts extends KafkaSpout {
    public LiveLogSpouts() {
        super( new KafkaSpoutConfig("kafka.properties").getLiveTopicCofig());
    }
}
