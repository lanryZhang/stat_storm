package com.ifeng;

import com.ifeng.storm.bolts.pagestat.NewsAppProfileBolts;
import com.ifeng.storm.bolts.pagestat.NewsLogAnalysisBolts;
import com.ifeng.storm.bolts.pagestat.WapNewsLogInitializeBolts;
import com.ifeng.storm.spouts.pagestat.WapNewsLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhanglr on 2016/10/9.
 */
public class WapNewsStatServer {
    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new WapNewsLogSpouts(), 20);
            builder.setBolt("loginitialize", new WapNewsLogInitializeBolts(), 20).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new NewsLogAnalysisBolts(), 20).fieldsGrouping("loginitialize",new Fields("pageStatInfo"));
            builder.setBolt("loganlysis", new NewsAppProfileBolts(), 20).fieldsGrouping("loginitialize",new Fields("pageStatInfo"));
//            builder.setBolt("logstat", new NewsLogStatBolts(), 1).fieldsGrouping("loganlysis", new Fields("pageStatInfo"));

           /* builder.setSpout("logreader", new BandWidthSpout(), 1).setNumTasks(1);
            builder.setBolt("loginitialize", new BandWidthInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("logstat", new BandWidthStatBolts(), 1).setNumTasks(1).fieldsGrouping("loginitialize", new Fields("dateTime"));*/

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS,0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.put("kafkaTopic","wapNewsStatTopic");
            conf.put("kafkaTopicProfile","newsAppStatTopic");
            conf.setNumWorkers(10);
            conf.setNumAckers(0);
            StormSubmitter.submitTopology("WapNews-Stat-Topology", conf, builder.createTopology());

//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","wapNewsStatTopic");
//            conf.put("kafkaTopicProfile","newsAppStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//            localCluster.submitTopology("WapNews-Stat-Topology1", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
