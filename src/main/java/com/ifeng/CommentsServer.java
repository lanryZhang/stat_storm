package com.ifeng;

import com.ifeng.storm.bolts.comments.CommentsAnalysisBolts;
import com.ifeng.storm.bolts.comments.CommentsInitializeBolts;
import com.ifeng.storm.spouts.comments.CommentsSpouts;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by duanyb on 2016/12/23.
 */
public class CommentsServer {

    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new CommentsSpouts(), 20);
            builder.setBolt("loginitialize", new CommentsInitializeBolts(), 20).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new CommentsAnalysisBolts(), 10).fieldsGrouping("loginitialize", new Fields("commentsInfo"));

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.setNumWorkers(5);
            conf.setNumAckers(0);
            conf.put("kafkaTopic", "commentsStatTopic");
            StormSubmitter.submitTopology("Comments-Stat-Topology", conf, builder.createTopology());

//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","commentsStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//
//            localCluster.submitTopology("Media-Stat-Topology2", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
