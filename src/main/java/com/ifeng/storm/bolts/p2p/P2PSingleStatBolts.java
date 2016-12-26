package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.P2PNatEntity;
import com.ifeng.entities.P2PSingleEntity;
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
 * Created by gutc on 2016/8/22.
 */
public class P2PSingleStatBolts extends BaseRichBolt {
    private FlushHashMap<String, P2PSingleEntity> flushHashMap;
    private FlushHashMap<String, P2PNatEntity> natFlushHashMap;
    private OutputCollector collector;

    @Override
    public void cleanup() {

    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String colName = (String) tuple.getValue(3);
            String dateTime = (String)tuple.getValue(0);
            if(tuple.getValue(1)!=null){
                P2PSingleEntity en = (P2PSingleEntity) tuple.getValue(1);
                flushHashMap.put(colName, dateTime, en);
            }
            if(tuple.getValue(2)!=null){
                P2PNatEntity en = (P2PNatEntity) tuple.getValue(2);
                natFlushHashMap.put(colName,en.getDateTime(), (P2PNatEntity) en.clone());
                natFlushHashMap.put(colName,en.getNetName()+en.getDateTime(), (P2PNatEntity) en.clone());
            }
            // System.err.println("执行了："+this.hashCode() +" "+colName + ":" + en.toString());

        } catch (Exception err) {

        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        flushHashMap = new FlushHashMap<>();
        natFlushHashMap = new FlushHashMap<>();
        flushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        natFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("dateTime","p2psingle","p2pnat","colname"));
    }
}
