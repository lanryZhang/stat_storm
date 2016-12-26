package com.ifeng.storm.bolts.ipserver;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.IpsEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class IpsLogAnalysisBolts extends BaseRichBolt {
    private static List<List<String>> cols = new ArrayList<>();
    private static Map<String, Field> fieldMap = new HashMap<>();
    private OutputCollector collector;
    private TopologyContext context;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        cols = new FieldsCombination().getIpsCols();
        Field[] fields = IpsEntity.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        this.collector = outputCollector;
        context = topologyContext;
    }

    @Override
    public void execute(Tuple tuple) {
        IpsEntity en = (IpsEntity) tuple.getValue(0);

        try {
            for (List<String> item : cols) {
                IpsEntity t = new IpsEntity();
                for (String inner : item) {
                    if (fieldMap.containsKey(inner)) {
                        Field f = fieldMap.get(inner);
                        f.set(t, f.get(en));
                    }
                }
                t.setHm(en.getHm());
                t.setCreateDate(en.getCreateDate());
                t.setDateTime(en.getDateTime());
                collector.emit(tuple,new Values(t.clone(),StringUtils.join(item, "_")));
            }
         /*   if(en.getIsOver().equals("false")){
                for (List<String> item : cols) {
                    IpsEntity ipsEntity = new IpsEntity();
                    for (String inner : item) {
                        if (fieldMap.containsKey(inner)) {
                            Field f = fieldMap.get(inner);
                            f.set(ipsEntity, f.get(en));
                        }
                    }
                    ipsEntity.setHm(en.getHm());
                    ipsEntity.setCreateDate(en.getCreateDate());
                    ipsEntity.setDateTime(en.getDateTime());
                    collector.emit(new Values(ipsEntity,"real_"+StringUtils.join(item, "_")));
                }
            }*/
        } catch (Exception err) {
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("ipslog","colname"));
    }
}
