/*
* HBaseLogFlush.java 
* Created on  202016/10/14 14:07 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

import com.ifeng.core.SerializeUtil;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.hbase.HBaseClient;
import com.ifeng.hbase.IHBaseClient;
import com.ifeng.kafka.KafkaProducerEx;
import com.ifeng.kafka.ProducerFactory;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class HBaseLogFlush implements IFlush {
    private String topicName;
    public HBaseLogFlush(){

    }
    KafkaProducerEx<String, String> producer = ProducerFactory.getBacthInstnace();
//    private static final Logger logger = Logger.getLogger(HBaseLogFlush.class);

    private ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> combineMap(ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> m,
                                                                                                   ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> n) {

        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> ite = m.size() > n.size() ? n : m;
        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> res = m.size() > n.size() ? m : n;
        for (Map.Entry<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> item : ite.entrySet()) {
            for (Map.Entry<PageStatInfo, PageStatCounter> in : item.getValue().entrySet()) {
                PageStatCounter v = in.getValue();
                if (res.containsKey(item.getKey())) {
                    if (res.get(item.getKey()).containsKey(in.getKey())) {
                        in.getValue().add(v);
                        res.get(item.getKey()).put(in.getKey(), in.getValue());
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void flush(Object... objects) {
        //IHBaseClient client = new HBaseClient();


        List<PageStatInfo> list = new ArrayList<>();

        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> map = (ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>>) objects[0];
//        ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> faildMap = (ConcurrentHashMap<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>>) objects[1];
//        map = combineMap(map, faildMap);
        try {
            for (Map.Entry<String, ConcurrentHashMap<PageStatInfo, PageStatCounter>> item : map.entrySet()) {
                try {
                    //client.changeTable("com_ifeng_pgcstats", item.getKey());
                    for (Map.Entry<PageStatInfo, PageStatCounter> entry : item.getValue().entrySet()) {
                        entry.getKey().setPv(entry.getValue().getPv());
                        entry.getKey().setVv(entry.getValue().getVv());
                        entry.getKey().setUv(entry.getValue().getUv());
                        entry.getKey().setComments(entry.getValue().getComments());
                        producer.send(new ProducerRecord<>("pgcStatsData", SerializeUtil.toJsonString(entry.getKey())));

                        list.add(entry.getKey());
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
                try {

                    //client.insertMany(list);
                    //client.incr(list);
                } catch (Exception er) {
                    er.printStackTrace();
                   // faildMap.put(item.getKey(), item.getValue());
                }
                list.clear();
            }
        } finally {
            if (producer != null) {
                producer.flush();
                producer.close();
            }

        }
    }
}
