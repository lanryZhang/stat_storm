package com.ifeng.configuration;


import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by zhanglr on 2016/3/30.
 */
public class KafkaSpoutConfig extends PropertiesConfig {

    public KafkaSpoutConfig(String path) {
        super(path);
    }

    public SpoutConfig getBandwidthTopicConfig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("bandwidth_topic_name"),pro.getProperty("bandwidthZkRoot"), pro.getProperty("bandwidth_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));

        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("bandwidth_id");
        config.zkRoot = pro.getProperty("bandwidthZkRoot");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime =-1;
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        return config;
    }

    public SpoutConfig getP2PTopicConfig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("p2p_topic_name"),pro.getProperty("p2pZkRoot"), pro.getProperty("p2p_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("p2p_id");
        config.zkRoot = pro.getProperty("p2pZkRoot");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        return config;
    }

    public SpoutConfig getLiveTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("live_topic_name"),pro.getProperty("liveZkRoot"), pro.getProperty("live_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("live_id");
        config.zkRoot = pro.getProperty("liveZkRoot");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        return config;
    }

    public SpoutConfig getTestTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("test_topic_name"),pro.getProperty("testZkRoot"), pro.getProperty("test_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("test_id");
        config.zkRoot = pro.getProperty("testZkRoot");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        return config;
    }

    public SpoutConfig getIpsTopicConfig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("ips_topic_name"),pro.getProperty("ipsZkRoot"), pro.getProperty("ips_new_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));

        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("ips_id");
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.zkRoot = pro.getProperty("ipsZkRoot");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    public Properties getProducerConfig(){
        Properties pro = this.getProperties();
        Properties producerPro = new Properties();
        producerPro.put("batch.size", pro.getProperty("batch.size"));
        producerPro.put("key.serializer", pro.getProperty("key.serializer.class"));
        producerPro.put("value.serializer", pro.getProperty("value.serializer.class"));
        producerPro.put("request.required.acks", pro.getProperty("request.required.acks"));
        producerPro.put("bootstrap.servers",pro.get("bootstrap.servers"));
        producerPro.put("timeout",pro.getProperty("timeout"));
        producerPro.put("message.send.max.retries",pro.get("message.send.max.retries"));
        return producerPro;
    }


    public SpoutConfig getMediaTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("media_topic_name"),pro.getProperty("mediaZkRoot"), pro.getProperty("media_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.id = pro.getProperty("media_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    public SpoutConfig getNewsAppTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("newsApp_topic_name"),pro.getProperty("newsAppZkRoot"), pro.getProperty("newsApp_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.id = pro.getProperty("newsApp_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    public SpoutConfig getVAppTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("vApp_topic_name"),pro.getProperty("vAppZkRoot"), pro.getProperty("vApp_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.id = pro.getProperty("vApp_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    public SpoutConfig getWapNewsTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("wapnews_topic_name"),pro.getProperty("wapNewsZkRoot"), pro.getProperty("wapnews_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.id = pro.getProperty("wapnews_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    public SpoutConfig getWebNewsTopicCofig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("webNews_topic_name"),pro.getProperty("webNewsZkRoot"), pro.getProperty("webNews_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));
        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.useStartOffsetTimeIfOffsetOutOfRange = true;
        config.id = pro.getProperty("webNews_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        config.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        return config;
    }

    /**
     *  vdn Spout config
     * @return
     */
    public SpoutConfig getVdnPcConfig(){
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("vdnpc_topic_name"),pro.getProperty("zkRoot"), pro.getProperty("vdnpc_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));

        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("vdnpc_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());

        return config;
    }

    public SpoutConfig getCommentsTopicCofig() {
        Properties pro = this.getProperties();

        BrokerHosts hosts =new ZkHosts(pro.getProperty("metadata.broker.list"));

        SpoutConfig config = new SpoutConfig(hosts,
                pro.getProperty("comments_topic_name"),pro.getProperty("commentsZkRoot"), pro.getProperty("comments_id"));

        config.zkServers = Arrays.asList(pro.getProperty("zookeeper.connect").split(","));
        config.zkPort=Integer.parseInt(pro.getProperty("zookeeper.port"));

        config.ignoreZkOffsets = Boolean.parseBoolean(pro.getProperty("ignoreZkOffsets"));
        config.id = pro.getProperty("comments_id");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());

        return config;
    }
}
