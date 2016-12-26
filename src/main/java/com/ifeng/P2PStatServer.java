package com.ifeng;

import com.ifeng.storm.bolts.p2p.*;
import com.ifeng.storm.spouts.p2p.P2PLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;


/**
 * Created by zhanglr on 2016/5/31.
 */
public class P2PStatServer {
    public static void main(String[] args) {
        try {
           /* TestProducer producerThread = new TestProducer("testP2P");
            producerThread.start();*/
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new P2PLogSpouts(), 40);
            builder.setBolt("loginitialize", new P2PInitializeBolts(), 20).shuffleGrouping("logreader");
            builder.setBolt("logSave", new P2PSaveBolts(), 10).shuffleGrouping("loginitialize");
            builder.setBolt("loganalysis", new PeerLogAnalysisBolts(), 20).shuffleGrouping("loginitialize");
            builder.setBolt("logstat", new PeerLogStatBolts(),10).fieldsGrouping("loganalysis", new Fields("logEntity"));
            builder.setBolt("singleanalysis", new P2PSingleAnalysisBolts(), 40).shuffleGrouping("loginitialize");
            builder.setBolt("singlestat", new P2PSingleStatBolts(),10).fieldsGrouping("singleanalysis", new Fields("dateTime"));

           /* TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new P2PLogSpouts(), 3).setNumTasks(6);
            builder.setBolt("loginitialize", new P2PInitializeBolts(), 3).setNumTasks(6).shuffleGrouping("logreader");
            builder.setBolt("logSave", new P2PSaveBolts(), 1).setNumTasks(2).shuffleGrouping("loginitialize");
            builder.setBolt("loganalysis", new PeerLogAnalysisBolts(), 1).setNumTasks(2).shuffleGrouping("loginitialize");
            builder.setBolt("logstat", new PeerLogStatBolts(),1).setNumTasks(2).fieldsGrouping("loganalysis", new Fields("logEntity"));
            builder.setBolt("singleanalysis", new P2PSingleAnalysisBolts(), 1).setNumTasks(2).shuffleGrouping("loginitialize");
            builder.setBolt("singlestat", new P2PSingleStatBolts(),1).setNumTasks(2).fieldsGrouping("singleanalysis", new Fields("dateTime"));*/

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 10000);
            conf.setNumWorkers(8);
            StormSubmitter.submitTopology("P2P-Stat-Topology", conf, builder.createTopology());

          /*  Config conf = new Config();
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            conf.put(Config.TOPOLOGY_DEBUG, true);
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("P2P-Test-Topology", conf, builder.createTopology());*/

            //         localCluster.shutdown();
        }  catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}