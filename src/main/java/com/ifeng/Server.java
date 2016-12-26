
package com.ifeng;

import com.ifeng.storm.bolts.ipserver.IpsLogAnalysisBolts;
import com.ifeng.storm.bolts.ipserver.IpsLogInitializeBolts;
import com.ifeng.storm.bolts.ipserver.IpsLogStatBolts;
import com.ifeng.storm.spouts.ipserver.IpsLogSpouts;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class Server {
    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new IpsLogSpouts(),40);
            builder.setBolt("loginitialize", new IpsLogInitializeBolts(), 40).shuffleGrouping("logreader");
            builder.setBolt("loganalysis", new IpsLogAnalysisBolts(), 80).shuffleGrouping("loginitialize");
            builder.setBolt("logstat", new IpsLogStatBolts(),40).setNumTasks(80).fieldsGrouping("loganalysis", new Fields("ipslog"));

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, false);
            conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 16);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 100);
            conf.setNumWorkers(16);
            conf.setNumAckers(16);
            StormSubmitter.submitTopology("IPS-Stat-Toplogie", conf, builder.createTopology());

          /*  TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("logreader", new IpsLogSpouts(),1).setNumTasks(1);
            builder.setBolt("loginitialize", new IpsLogInitializeBolts(), 1).setNumTasks(1).shuffleGrouping("logreader");
            builder.setBolt("loganalysis", new IpsLogAnalysisBolts(), 1).setNumTasks(1).shuffleGrouping("loginitialize");
            builder.setBolt("logstat", new IpsLogStatBolts(),1).setNumTasks(1).fieldsGrouping("loganalysis", new Fields("ipslog"));

            Config conf = new Config();
            conf.put(Config.TOPOLOGY_DEBUG, true);
            conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            conf.setNumWorkers(1);
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("IPS-Stat-Toplogie", conf, builder.createTopology());*/

           // LocalCluster localCluster = new LocalCluster();
            //localCluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
//            //TridentTopology topology = new TridentTopology();

            //remote Submit
//        Config conf = new Config();
//        conf.setNumWorkers(2);
//        conf.setDebug(true);
//
//        Map stormConf = Utils.readStormConfig();
//        stormConf.put("nimbus.host", "10.90.3.38");
//        stormConf.putAll(conf);
//
//        Nimbus.Client client = NimbusClient.getConfiguredClient(stormConf).getClient();
//        String inputJar = "D:\\SourceCode\\JavaIntellij\\IpsLogCollect\\out\\IpsLogCollect.jar";
//
//            NimbusClient nimbus = new NimbusClient(stormConf, "10.90.3.38", 6627);
//
//
//// 使用 StormSubmitter 提交 jar 包
//        String uploadedJarLocation = StormSubmitter.submitJar(stormConf, inputJar);
//        String jsonConf = JSONValue.toJSONString(stormConf);
//        nimbus.getClient().submitTopology("remotetopology", uploadedJarLocation, jsonConf, builder.createTopology());
  //          Thread.sleep(1000);
            //localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}