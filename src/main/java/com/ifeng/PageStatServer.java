package com.ifeng;

import com.ifeng.storm.bolts.pagestat.IfengNewsLogInitializeBolts;
import com.ifeng.storm.bolts.pagestat.NewsLogAnalysisBolts;
import com.ifeng.storm.spouts.pagestat.PageLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhanglr on 2016/10/9.
 */
public class PageStatServer {
    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new PageLogSpouts(), 40);
            builder.setBolt("loginitialize", new IfengNewsLogInitializeBolts(), 40).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new NewsLogAnalysisBolts(), 40).fieldsGrouping("loginitialize", new Fields("pageStatInfo"));

            //builder.setBolt("loganlysis", new NewsAppProfileBolts(), 40).fieldsGrouping("loginitialize", new Fields("pageStatInfo"));
//            builder.setBolt("logstat", new NewsLogStatBolts(), 1).fieldsGrouping("loganlysis", new Fields("pageStatInfo"));

           /* builder.setSpout("logreader", new BandWidthSpout(), 1).setNumTasks(1);
            builder.setBolt("loginitialize", new BandWidthInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("logstat", new BandWidthStatBolts(), 1).setNumTasks(1).fieldsGrouping("loginitialize", new Fields("dateTime"));*/

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.setNumWorkers(16);
            conf.setNumAckers(0);
            conf.put("kafkaTopic", "webNewsStatTopic");
            conf.put("kafkaTopicProfile", "newsAppStatTopic");
            StormSubmitter.submitTopology("WebNews-Stat-Topology", conf, builder.createTopology());
//
//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","webNewsStatTopic");
//            conf.put("kafkaTopicProfile","newsAppStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//            localCluster.submitTopology("test-Stat-Topology1", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
