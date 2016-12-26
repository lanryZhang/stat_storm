package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.P2PServerDurationEntity;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by gutc on 2016/8/25.
 */
public class P2PServerDurationAnalysisBolts extends BaseRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            if(tuple.getValue(7)!=null){
                P2PServerDurationEntity en = (P2PServerDurationEntity) tuple.getValue(7);
                if(en.getDuration()<=(5*60)){
                    en.setDuration(5);
                }else if(en.getDuration()>(5*60)&&en.getDuration()<=(10*60)){
                    en.setDuration(10);
                }else if(en.getDuration()>(10*60)&&en.getDuration()<=(20*60)){
                    en.setDuration(20);
                }else if(en.getDuration()>(20*60)&&en.getDuration()<=(40*60)){
                    en.setDuration(40);
                }else{
                    en.setDuration(50);
                }
                collector.emit(new Values(en.getDate()+en.getDuration(),en,"p2p_server_duration"));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("duration","p2pServerDuration","colname"));
    }
}
