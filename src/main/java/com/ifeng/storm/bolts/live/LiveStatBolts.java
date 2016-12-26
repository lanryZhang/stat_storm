package com.ifeng.storm.bolts.live;

import com.ifeng.entities.LiveLogEntities;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.LiveLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveStatBolts extends BaseRichBolt {
    private FlushHashMap<String, LiveLogEntities> flushHashMap;
    private OutputCollector collector;

    @Override
    public void cleanup() {

    }

    @Override
    public void execute(Tuple tuple) {
        try {
            if(tuple.getValue(0)!=null){
                LiveLogEntities en = (LiveLogEntities) tuple.getValue(0);
                flushHashMap.put("live_stat", en.getCreateTime(), (LiveLogEntities) en.clone());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        collector.ack(tuple);
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        flushHashMap = new FlushHashMap<String, LiveLogEntities>();
        flushHashMap.setFlusher(new LiveLogFlush("livestat"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("livestat"));
    }
}
