package com.ifeng;

import com.ifeng.storm.bolts.pagestat.NewsAppLogInitializeBolts;
import com.ifeng.storm.bolts.pagestat.NewsAppProfileBolts;
import com.ifeng.storm.bolts.pagestat.NewsLogAnalysisBolts;
import com.ifeng.storm.spouts.pagestat.NewsAppLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhanglr on 2016/10/9.
 */
public class NewsAppStatServer {
    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new NewsAppLogSpouts(), 40);
            builder.setBolt("loginitialize", new NewsAppLogInitializeBolts(), 40).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new NewsLogAnalysisBolts(), 10).fieldsGrouping("loginitialize", new Fields("pageStatInfo"));
            builder.setBolt("logProfileAnlysis", new NewsAppProfileBolts(), 10).fieldsGrouping("loginitialize", new Fields("pageStatInfo"));

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.setNumWorkers(16);
            conf.put("kafkaTopic","newsAppStatTopic");
            conf.put("kafkaTopicProfile","userDescriptionStatTopic");
            conf.setNumAckers(0);
            StormSubmitter.submitTopology("NewsApp-Stat-Topology", conf, builder.createTopology());

//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","newsAppStatTopic");
//            conf.put("kafkaTopicProfile","userDescriptionStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//            localCluster.submitTopology("NewsApp-Stat-Topology1", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
