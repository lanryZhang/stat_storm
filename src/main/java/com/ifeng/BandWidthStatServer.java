package com.ifeng;

import com.ifeng.storm.bolts.bandwidth.BandWidthInitializeBolts;
import com.ifeng.storm.bolts.bandwidth.BandWidthStatBolts;
import com.ifeng.storm.spouts.bandwidth.BandWidthSpout;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class BandWidthStatServer {
    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new BandWidthSpout(), 40).setNumTasks(80);
            builder.setBolt("loginitialize", new BandWidthInitializeBolts(), 40).setNumTasks(80).shuffleGrouping("logreader");
            builder.setBolt("logstat", new BandWidthStatBolts(), 40).setNumTasks(80).fieldsGrouping("loginitialize", new Fields("dateTime"));

           /* builder.setSpout("logreader", new BandWidthSpout(), 1).setNumTasks(1);
            builder.setBolt("loginitialize", new BandWidthInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("logstat", new BandWidthStatBolts(), 1).setNumTasks(1).fieldsGrouping("loginitialize", new Fields("dateTime"));*/

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS,0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 100000);
            conf.setNumWorkers(12);
            StormSubmitter.submitTopology("Bandwidth-Stat-Topology", conf, builder.createTopology());
            Thread.sleep(1000);

           /* Config conf = new Config();
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            conf.put(Config.TOPOLOGY_DEBUG, true);
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("Nginx-Stat-Topology", conf, builder.createTopology());
            Thread.sleep(1000);*/

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
