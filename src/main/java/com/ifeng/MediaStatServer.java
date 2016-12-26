package com.ifeng;

import com.ifeng.storm.bolts.media.MediaLogAnalysisBolts;
import com.ifeng.storm.bolts.media.MediaLogInitializeBolts;
import com.ifeng.storm.spouts.media.MediaLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by duanyb on 2016/11/18.
 */
public class MediaStatServer {

    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new MediaLogSpouts(), 20);
            builder.setBolt("loginitialize", new MediaLogInitializeBolts(), 20).shuffleGrouping("logreader");
            builder.setBolt("loganlysis", new MediaLogAnalysisBolts(), 20).fieldsGrouping("loginitialize", new Fields("mediaLogInfo"));
            //builder.setBolt("logpgcstat", new MediaLogPgcIdBolts(), 40).fieldsGrouping("loganlysis", new Fields("mediaAnaLogInfo"));

           /* builder.setSpout("logreader", new BandWidthSpout(), 1).setNumTasks(1);
            builder.setBolt("loginitialize", new BandWidthInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("logstat", new BandWidthStatBolts(), 1).setNumTasks(1).fieldsGrouping("loginitialize", new Fields("dateTime"));*/

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 0);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
            conf.setNumWorkers(10);
            conf.setNumAckers(0);
            conf.put("kafkaTopic", "mediaStatTopic");
            StormSubmitter.submitTopology("Media-Stat-Topology", conf, builder.createTopology());

//            Config conf = new Config();
//            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 60);
//            conf.put(Config.TOPOLOGY_DEBUG, true);
//            conf.put("kafkaTopic","mediaStatTopic");
//            LocalCluster localCluster = new LocalCluster();
//
//            localCluster.submitTopology("Media-Stat-Topology2", conf, builder.createTopology());

            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
