package com.ifeng.storm.bolts.p2p;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.PeerInfo;
import com.ifeng.entities.PeerLogEntity;
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
public class PeerLogAnalysisBolts extends BaseRichBolt {
    private List<List<String>> cols = new ArrayList<List<String>>();
    private Map<String, Field> fieldMap = new HashMap<String, Field>();
    private OutputCollector collector;
    private TopologyContext context;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        cols = new FieldsCombination().getPeerLogCols();
        Field[] fields = PeerLogEntity.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        this.collector = outputCollector;
        context = topologyContext;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            if(tuple.getValue(0)!=null&&tuple.getValue(1)!=null){
                PeerLogEntity en = (PeerLogEntity) tuple.getValue(0);
                PeerInfo pinfo = (PeerInfo)tuple.getValue(1);
                for (List<String> item : cols) {
                    PeerLogEntity t = new PeerLogEntity();
                    for (String inner : item) {
                        if (fieldMap.containsKey(inner)) {
                            Field f = fieldMap.get(inner);
                            f.set(t, f.get(en));
                        }
                    }
                    t.setDate(en.getDate());
                    t.setTime(en.getTime());
                    t.setDateTime(en.getDateTime());

                    collector.emit(new Values(t,pinfo.clone(),StringUtils.join(item, "_")));
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
        outputFieldsDeclarer.declare(new Fields("logEntity","p2pinfo","colname"));
    }
}
