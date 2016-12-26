package com.ifeng.storm.bolts.comments;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.CommentsInfo;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IFlush;
import com.ifeng.persistence.comments.CommentsKafKaFlush;
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
 * Created by duanyb on 2016/12/23.
 */
public class CommentsAnalysisBolts extends BaseRichBolt {
    private List<List<String>> cols = new ArrayList<>();
    private Map<String, Field> fieldMap = new HashMap<>();
    private FlushHashMap<CommentsInfo, CommentsInfo> flushHashMap;
    private OutputCollector collector;
    private TopologyContext context;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        cols = new FieldsCombination().getCommectsCols();
        Field[] fields = CommentsInfo.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        this.collector = outputCollector;
        context = topologyContext;
        flushHashMap = new FlushHashMap<>(305);
        IFlush iFlush = new CommentsKafKaFlush(map.get("kafkaTopic").toString());
        flushHashMap.setFlusher(iFlush);
    }

    @Override
    public void execute(Tuple tuple) {
        CommentsInfo en = (CommentsInfo) tuple.getValue(0);
        try {
            for (List<String> item : cols) {
                CommentsInfo t = new CommentsInfo();

                for (String inner : item) {
                    if (fieldMap.containsKey(inner)) {
                        Field f = fieldMap.get(inner);
                        f.set(t, f.get(en));
                    }
                }

                String tableName = StringUtils.join(item, "_");
                CommentsInfo commentsInfo = new CommentsInfo();
                commentsInfo.setComments(1L);

                flushHashMap.put(tableName, t, commentsInfo);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            collector.ack(tuple);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("commentsStatInfo"));
    }
}