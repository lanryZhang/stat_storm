package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.*;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.P2PLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;
import java.util.UUID;

/**
 * Created by gutc on 2016/7/12.
 */
public class P2PSaveBolts extends BaseRichBolt {
    private OutputCollector collector;
    private FlushHashMap<String, P2PAcceptEntity> acceptFlushHashMap;
    private FlushHashMap<String, P2PSendEntity> sendFlushHashMap;
    private FlushHashMap<String, P2PSingleEntity> singleFlushHashMap;
    private FlushHashMap<String, P2PServerCountEntity> countFlushHashMap;
    private FlushHashMap<String, P2PServerProgramTopEntity> programTopFlushHashMap;
    private FlushHashMap<String, P2PServerDurationEntity> durationFlushHashMap;
    private FlushHashMap<String, P2PNatEntity> natFlushHashMap;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        acceptFlushHashMap = new FlushHashMap<>();
        sendFlushHashMap = new FlushHashMap<>();
        singleFlushHashMap = new FlushHashMap<>();
        countFlushHashMap = new FlushHashMap<>();
        programTopFlushHashMap = new FlushHashMap<>();
        durationFlushHashMap = new FlushHashMap<>();
        natFlushHashMap = new FlushHashMap<>();
        acceptFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        sendFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        singleFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        countFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        programTopFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        durationFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
        natFlushHashMap.setFlusher(new P2PLogFlush("p2pstat"));
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String colName;
            String colNameFull;
           // String col;
            if(tuple.getValue(2)!=null){
                P2PAcceptEntity p2PAcceptEntity = (P2PAcceptEntity) tuple.getValue(2);
                colName = "p2p_res";
                UUID uuid = UUID.randomUUID();
                acceptFlushHashMap.put(colName,uuid.toString(),p2PAcceptEntity);
            }
            if( tuple.getValue(3)!=null){
                P2PSendEntity p2PSendEntity = (P2PSendEntity) tuple.getValue(3);
                colName = "p2p_send";
                UUID uuid = UUID.randomUUID();
                sendFlushHashMap.put(colName,uuid.toString() , p2PSendEntity);

            }
            if(tuple.getValue(4)!=null){
                P2PSingleEntity p2PSingleEntity = (P2PSingleEntity) tuple.getValue(4);
                colNameFull = "p2p_single";
               // colName = "p2p_single_dateTime";
                UUID uuid = UUID.randomUUID();
                singleFlushHashMap.put(colNameFull,uuid.toString(), (P2PSingleEntity) p2PSingleEntity.clone());
               // singleFlushHashMap.put(colName,p2PSingleEntity.getDateTime(), (P2PSingleEntity) p2PSingleEntity.clone());
            }
            if(tuple.getValue(5)!=null){
                P2PServerCountEntity p2PServerCountEntity = (P2PServerCountEntity) tuple.getValue(5);
                colName = "p2p_server_count";
                UUID uuid = UUID.randomUUID();
                countFlushHashMap.put(colName,uuid.toString(),p2PServerCountEntity);
            }
            if(tuple.getValue(6)!=null){
                P2PServerProgramTopEntity p2PServerProgramTopEntity = (P2PServerProgramTopEntity) tuple.getValue(6);
                colName = "p2p_server_program_top";
                UUID uuid = UUID.randomUUID();
                programTopFlushHashMap.put(colName,uuid.toString(),p2PServerProgramTopEntity);
            }
           /* if(tuple.getValue(8)!=null){
                P2PNatEntity p2PNatEntity = (P2PNatEntity) tuple.getValue(8);
                colName = "p2p_nat_dateTime";
                colNameFull = "p2p_nat";
               // col = "p2p_nat_all";
               // UUID uuid = UUID.randomUUID();
               // natFlushHashMap.put(col,uuid.toString(),p2PNatEntity);
                natFlushHashMap.put(colName,p2PNatEntity.getDateTime(), (P2PNatEntity) p2PNatEntity.clone());
                natFlushHashMap.put(colNameFull,p2PNatEntity.getNetName()+p2PNatEntity.getDateTime(), (P2PNatEntity) p2PNatEntity.clone());
            }*/
            if(tuple.getValue(7)!=null) {
                P2PServerDurationEntity en = (P2PServerDurationEntity) tuple.getValue(7);
                if (en.getDuration() <= (5 * 60)) {
                    en.setDuration(5);
                } else if (en.getDuration() > (5 * 60) && en.getDuration() <= (10 * 60)) {
                    en.setDuration(10);
                } else if (en.getDuration() > (10 * 60) && en.getDuration() <= (20 * 60)) {
                    en.setDuration(20);
                } else if (en.getDuration() > (20 * 60) && en.getDuration() <= (40 * 60)) {
                    en.setDuration(40);
                } else {
                    en.setDuration(50);
                }
                durationFlushHashMap.put("p2p_server_duration",en.getDate()+en.getDuration(),en);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("p2pRes","p2pSend","p2pSingle","p2pServerCount"));
    }
}
