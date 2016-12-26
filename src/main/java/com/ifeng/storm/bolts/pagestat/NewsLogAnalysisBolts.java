/*
* PageAnalysisBolts.java 
* Created on  202016/10/14 13:54 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.storm.bolts.pagestat;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IFlush;
import com.ifeng.persistence.newapp.NewAppKafkaFlush;
import com.ifeng.redis.RedisClient;
import com.ifeng.redis.RedisFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class NewsLogAnalysisBolts extends BaseRichBolt {
    private List<List<String>> cols = new ArrayList<>();
    private Map<String, Field> fieldMap = new HashMap<>();
    private FlushHashMap<PageStatInfo, PageStatCounter> flushHashMap;
    private OutputCollector collector;
    private TopologyContext context;
    private RedisClient client;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        cols = new FieldsCombination().getHBaseCols();
        Field[] fields = PageStatInfo.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        this.collector = outputCollector;
        context = topologyContext;

        flushHashMap = new FlushHashMap<>(305);
        IFlush iFlush = new NewAppKafkaFlush(map.get("kafkaTopic").toString());
        flushHashMap.setFlusher(iFlush);
        client = RedisFactory.newInstance();
    }

    @Override
    public void execute(Tuple tuple) {
        PageStatInfo en = (PageStatInfo) tuple.getValue(0);
        try {
            if ("video".equals(en.getType())) {
                String pgcid = client.getString(en.getMediaId());
                if (pgcid == null) {
                    return;
                }else{
                    en.setPgcId(pgcid.replace("\"", ""));
                }
            }
            for (List<String> item : cols) {
                PageStatInfo t = new PageStatInfo();
                StringBuilder md5Key = new StringBuilder();
                StringBuilder dateTimeKey = new StringBuilder();
                for (String inner : item) {
                    if (fieldMap.containsKey(inner)) {
                        Field f = fieldMap.get(inner);
                        f.set(t, f.get(en));
                        if (!f.getName().equals("createDate") && !f.getName().equals("createTime")) {
                            md5Key.append(f.get(en));
                        } else {
                            dateTimeKey.append(f.get(en)).append(" ");
                        }
                    }
                }

                String tableName = StringUtils.join(item, "_");

                t.setType(en.getType());
                t.setUrl(en.getUrl());

                PageStatCounter pageStatCounter = new PageStatCounter();
                pageStatCounter.setPv(1L);
                pageStatCounter.setVv(1L);
                pageStatCounter.setUv(1L);


                if("video".equals(en.getType())){
                    String distKey = t.getPgcId() + t.getMediaId()  + en.getUid();
                    flushHashMap.putDistinct(tableName, t, pageStatCounter, distKey);
                }else{
                    flushHashMap.put(tableName, t, pageStatCounter);
                }
                //collector.emit(tuple,new Values(t, counter, tableName,en.getUid() + en.getUrl()));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            collector.ack(tuple);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("pageStatInfo"));
    }
}
