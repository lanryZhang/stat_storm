package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.PeerInfo;
import com.ifeng.entities.PeerLogEntity;
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
 * Created by zhanglr on 2016/3/29.
 */
public class PeerLogStatBolts extends BaseRichBolt {
    private FlushHashMap<PeerLogEntity, PeerInfo> flushHashMap;
    private OutputCollector collector;

    @Override
    public void cleanup() {

    }

    @Override
    public void execute(Tuple tuple) {
        try {
            if(tuple.getValue(0)!=null){
                PeerLogEntity en = (PeerLogEntity) tuple.getValue(0);
                String colName = (String) tuple.getValue(2);
                PeerInfo pinfo = (PeerInfo)tuple.getValue(1);
                // System.err.println("执行了："+this.hashCode() +" "+colName + ":" + en.toString());
                flushHashMap.put(colName, en, pinfo);
            }
        } catch (Exception err) {

        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        flushHashMap = new FlushHashMap<>();
        flushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("logEntity","p2pinfo","colname"));
    }
}
