package com.ifeng.persistence.comments;

import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.CommentsInfo;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import com.ifeng.persistence.IFlush;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/12/24.
 */
public class CommentsKafKaFlush implements IFlush {
    private String topicName;
    private KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();
    public CommentsKafKaFlush(){

    }
    public CommentsKafKaFlush(String topicName){
        this.topicName = topicName;
    }

    @Override
    public void flush(Object... objects) {

        ConcurrentHashMap<String, ConcurrentHashMap<CommentsInfo, CommentsInfo>> map = (ConcurrentHashMap<String, ConcurrentHashMap<CommentsInfo, CommentsInfo>>) objects[0];

        try {
            for (Map.Entry<String, ConcurrentHashMap<CommentsInfo, CommentsInfo>> item : map.entrySet()) {
                try {
                    for (Map.Entry<CommentsInfo, CommentsInfo> entry : item.getValue().entrySet()) {
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
