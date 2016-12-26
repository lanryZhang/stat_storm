/*
* HBaseLogFlush.java 
* Created on  202016/10/14 14:07 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class KafkaFlush implements IFlush {
    private String topicName;
    private KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();
    public KafkaFlush(){

    }
    public KafkaFlush(String topicName){
        this.topicName = topicName;
    }

    @Override
    public void flush(Object... objects) {

        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> map = (ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>>) objects[0];

        try {
            for (Map.Entry<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> item : map.entrySet()) {
                try {
                    for (Map.Entry<PageStatInfo, PageStatCounter> entry : item.getValue().entrySet()) {
                        entry.getKey().setPv(entry.getValue().getPv());
                        entry.getKey().setVv(entry.getValue().getVv());
                        entry.getKey().setUv(entry.getValue().getUv());
                        entry.getKey().setComments(entry.getValue().getComments());
                        producer.sendBatch(new ProducerRecord<>(topicName, SerializeUtil.toJsonString(entry.getKey())));
                    }
                } catch (KafkaException err) {
                    if (producer != null) {
                        producer.close();
                        producer = ProducerFactory.getBacthInstnace();
                    }
                } catch (Exception er) {
                    er.printStackTrace();
                    //faildMap.put(item.getKey(), item.getValue());
                }
            }
        } finally {
            if (producer != null) {
                producer.flush();
            }
            map.clear();
        }
    }
}
