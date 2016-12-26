package com.ifeng.persistence.vapp;

import com.alibaba.fastjson.JSON;
import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.Comments;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import com.ifeng.persistence.IFlush;
import com.ifeng.utils.HttpAttr;
import com.ifeng.utils.HttpHelper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/11/17.
 */
public class VappKafkaFlush implements IFlush {

    private String topicName;
    private String url = "http://comment.ifeng.com/get";
    KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();

    public VappKafkaFlush(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public void flush(Object... objects) {

        HttpAttr attr = HttpAttr.getDefaultInstance();
        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> map = (ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>>) objects[0];
        try {
            for (Map.Entry<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> item : map.entrySet()) {
                try {
                    for (Map.Entry<PageStatInfo, PageStatCounter> entry : item.getValue().entrySet()) {
                        String  media = entry.getKey().getMediaId();
                        if(media != ""){
                            StringBuilder postData = new StringBuilder();
                            postData.append("job=4&doc_url=");
                            postData.append(media);
                            postData.append("&format=json&callback=newcomment");
                            String json = HttpHelper.postData(url, attr, postData.toString(), "utf-8");
                            List<Comments> comments = JSON.parseArray(json + "", Comments.class);
                            entry.getKey().setComments(comments.get(0).getAllcount());
                        }
                        entry.getKey().setPv(entry.getValue().getPv());
                        entry.getKey().setVv(entry.getValue().getVv());
                        entry.getKey().setUv(entry.getValue().getUv());
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