package com.ifeng.persistence.newapp;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.Comments;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import com.ifeng.persistence.IFlush;
import com.ifeng.utils.HttpAttr;
import com.ifeng.utils.HttpHelper;
import com.sun.deploy.net.HttpUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/11/16.
 */
public class NewAppKafkaFlush implements IFlush {

    private String topicName;
    private String requesturl = "http://comment.ifeng.com/get";
    private KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();
    public NewAppKafkaFlush() {
    }

    public NewAppKafkaFlush(String topicName) {
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


                        String url = entry.getKey().getUrl();
                        String media = entry.getKey().getMediaId();
                        String flag = url != "" ? url : media;
                        if (flag != "") {
                            StringBuilder postData = new StringBuilder();
                            postData.append("job=4&doc_url=");
                            postData.append(flag);
                            postData.append("&format=json&callback=newcomment");
                            String json = HttpHelper.postData(requesturl, attr, postData.toString(), "utf-8");
                            List<Comments> comments = JSON.parseArray(json + "", Comments.class);
                            entry.getKey().setComments(comments.get(0).getAllcount());
                        }

                        entry.getKey().setPv(entry.getValue().getPv());
                        entry.getKey().setVv(entry.getValue().getVv());
                        entry.getKey().setUv(entry.getValue().getUv());

                        producer.send(new ProducerRecord<>(topicName, SerializeUtil.toJsonString(entry.getKey())));
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
