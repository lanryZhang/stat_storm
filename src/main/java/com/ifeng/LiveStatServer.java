package com.ifeng;

import com.ifeng.storm.bolts.live.LiveInitializeBolts;
import com.ifeng.storm.bolts.live.LiveStatBolts;
import com.ifeng.storm.spouts.live.LiveLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveStatServer {
    public static void main(String[] args) {
        try {
           /* TestProducer producerThread = new TestProducer("testP2P");
            producerThread.start();*/

            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new LiveLogSpouts(), 1).setNumTasks(1);
            builder.setBolt("loginitialize", new LiveInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("livelogstat", new LiveStatBolts(),1).setNumTasks(1).fieldsGrouping("loginitialize", new Fields("livelogentity"));


            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 10000);
            conf.setNumWorkers(1);
//            LocalCluster localCluster = new LocalCluster();
//            localCluster.submitTopology("Live-Test-Topology", conf, builder.createTopology());
            StormSubmitter.submitTopology("Live-stat-Topology", conf, builder.createTopology());
        }  catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
