/*
* Producer.java 
* Created on  202016/11/4 17:48 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.kafka;

import com.ifeng.configuration.KafkaSpoutConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ProducerFactory {
    public static Producer getInstnace(){
        Properties pro = new KafkaSpoutConfig("kafka.properties").getProducerConfig();
        return new KafkaProducer<>(pro);
    }

    public static KafkaProducerEx getBacthInstnace(){
        Properties pro = new KafkaSpoutConfig("kafka.properties").getProducerConfig();
        return new KafkaProducerEx<>(pro);
    }
}
