package com.ifeng.persistence.profile;

import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.ZmtDescription;
import com.ifeng.entities.ZmtDescriptionCounter;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import com.ifeng.persistence.IFlush;
import com.ifeng.utils.HttpAttr;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/12/24.
 */
public class UserProfileKafkaFlush implements IFlush {

    private String topicName;
    private KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();
    public UserProfileKafkaFlush() {
    }

    public UserProfileKafkaFlush(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public void flush(Object... objects) {
        HttpAttr attr = HttpAttr.getDefaultInstance();
        ConcurrentHashMap<String, ConcurrentHashMap<ZmtDescription, ZmtDescriptionCounter>> map = (ConcurrentHashMap<String, ConcurrentHashMap<ZmtDescription, ZmtDescriptionCounter>>) objects[0];
        try {
            for (Map.Entry<String, ConcurrentHashMap<ZmtDescription, ZmtDescriptionCounter>> item : map.entrySet()) {
                try {
                    for (Map.Entry<ZmtDescription, ZmtDescriptionCounter> entry : item.getValue().entrySet()) {
                        entry.getKey().setCities(entry.getValue().getCities());
                        entry.getKey().setAreas(entry.getValue().getAreas());
                        entry.getKey().setGender(entry.getValue().getGender());
                        producer.send(new ProducerRecord<>(topicName, SerializeUtil.toJsonString(entry.getKey())));
                    }
                } catch (KafkaException err) {
                    if (producer != null) {
                        producer.close();
                        producer = ProducerFactory.getBacthInstnace();
                    }
                } catch (Exception er) {
                    er.printStackTrace();
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
