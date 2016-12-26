package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.P2PNatEntity;
import com.ifeng.entities.P2PSingleEntity;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by gutc on 2016/8/22.
 */
public class P2PSingleAnalysisBolts extends BaseRichBolt {
    private OutputCollector collector;
    private TopologyContext context;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        context = topologyContext;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            if(tuple.getValue(4)!=null){
                P2PSingleEntity en = (P2PSingleEntity) tuple.getValue(4);
                String dateTime = en.getDateTime();
               // String dateTimeGuid =en.getDateTime()+en.getGuid();
               // System.out.println("P2PSingleAnalysisBolts:"+dateTime + ":" + en.toString());
                collector.emit(new Values(dateTime, en, null,"p2p_single_dateTime"));
               // System.out.println("P2PSingleAnalysisBoltsTwo:" + dateTimeGuid + ":" + en.clone().toString());
               // collector.emit(new Values(dateTimeGuid, en.clone(), "p2p_single_guid_dateTime"));
            }
            if(tuple.getValue(8)!=null){
                P2PNatEntity p2PNatEntity = (P2PNatEntity) tuple.getValue(8);
                String dateTime = p2PNatEntity.getDateTime();
                String netName = p2PNatEntity.getNetName();
                collector.emit(new Values( dateTime,null, p2PNatEntity.clone(), "p2p_nat_dateTime"));
                collector.emit(new Values(netName+p2PNatEntity.getDateTime(), null, p2PNatEntity.clone(), "p2p_nat"));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("dateTime","p2psingle","p2pnat","colname"));
    }
}
