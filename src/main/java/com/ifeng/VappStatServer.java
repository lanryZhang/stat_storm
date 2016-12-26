package com.ifeng;

import com.ifeng.storm.bolts.vapp.VappLogInitializeBolts;
import com.ifeng.storm.bolts.vapp.VappLogPgcIdBolts;
import com.ifeng.storm.bolts.vapp.VappLogProfileBolts;
import com.ifeng.storm.bolts.vapp.VappLogsaveBolts;
import com.ifeng.storm.spouts.vapp.VappLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by duanyb on 2016/11/17.
 */
public class VappStatServer {

    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();

            builder.setSpout("logreader", new VappLogSpouts(), 20);
            builder.setBolt("loginitialize", new VappLogInitializeBolts(), 20).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new VappLogsaveBolts(), 10).fieldsGrouping("loginitialize", new Fields("vappUVInfo"));
            builder.setBolt("logProfileAnlysis", new VappLogProfileBolts(), 10).fieldsGrouping("loginitialize", new Fields("vappUVInfo"));
//
            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.setNumWorkers(10);
            conf.put("kafkaTopic","vappStatTopic");
            conf.put("kafkaTopicProfile","userDescriptionStatTopic");
            conf.setNumAckers(0);
            StormSubmitter.submitTopology("VApp-Stat-Topology", conf, builder.createTopology());
//
//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","vappStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//            conf.put("kafkaTopicProfile","newsAppStatTopic");
//            localCluster.submitTopology("VApp-Stat-Topology2", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
