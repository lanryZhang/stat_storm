package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.P2PServerDurationEntity;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.P2PLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by gutc on 2016/8/25.
 */
public class P2PServerDurationStatBolts  extends BaseRichBolt {
    private FlushHashMap<String, P2PServerDurationEntity> flushHashMap;
    private OutputCollector collector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        flushHashMap = new FlushHashMap<>();
        flushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            P2PServerDurationEntity en = (P2PServerDurationEntity) tuple.getValue(1);
            String colName = (String) tuple.getValue(2);
            String duration = (String)tuple.getValue(0);
            // System.err.println("执行了："+this.hashCode() +" "+colName + ":" + en.toString());
            flushHashMap.put(colName, duration, en);
        } catch (Exception err) {

        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("duration","p2pServerDuration","colname"));
    }
}
