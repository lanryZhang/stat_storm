package com.ifeng.storm.bolts.media;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IFlush;
import com.ifeng.persistence.KafkaFlush;
import com.ifeng.persistence.media.MediaKafkaFlush;
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
 * Created by duanyb on 2016/11/18.
 */
public class MediaLogAnalysisBolts extends BaseRichBolt {
    private FlushHashMap<PageStatInfo, PageStatCounter> flushHashMap;
    private List<List<String>> cols = new ArrayList<>();
    private Map<String, Field> fieldMap = new HashMap<>();
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
        IFlush iFlush = new MediaKafkaFlush(map.get("kafkaTopic").toString());
        flushHashMap.setFlusher(iFlush);
        client = RedisFactory.newInstance();
    }

    @Override
    public void execute(Tuple tuple) {
        PageStatInfo en = (PageStatInfo) tuple.getValue(0);

        try {
            if (en.getMediaId() != "") {
                String pgcid = client.getString(en.getMediaId());
                if (pgcid != null) {
                    en.setPgcId(pgcid.replace("\"", ""));
                    for (List<String> item : cols) {
                        PageStatInfo t = new PageStatInfo();
                        for (String inner : item) {
                            if (fieldMap.containsKey(inner)) {
                                Field f = fieldMap.get(inner);
                                f.set(t, f.get(en));
                            }
                        }
                        t.setUrl(en.getMediaId());
                        String tableName = StringUtils.join(item, "_");

                        PageStatCounter pageStatCounter = new PageStatCounter();
                        pageStatCounter.setPv(1L);
                        pageStatCounter.setUv(1L);
                        pageStatCounter.setVv(1L);

                        t.setType("video");
                        flushHashMap.putDistinct(tableName, t, pageStatCounter, t.getPgcId() + t.getMediaId() + en.getUid());

                    }
                }
            }


        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("mediaAnaLogInfo"));
    }
}