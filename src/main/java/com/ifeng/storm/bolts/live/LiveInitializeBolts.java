package com.ifeng.storm.bolts.live;

import com.ifeng.entities.LiveLogEntities;
import com.ifeng.core.misc.IpRange;
import com.ifeng.utils.DateUtil;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;
    private IpRange ipRange = new IpRange();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        ipRange.initRangeSet();
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] arr = sentence.split(" ");
        try {
            if(arr.length>10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String remoteIp = arr[0];
                String dateTime = arr[1].replace("[", "").trim();
                String date = DateUtil.formatDate(DateUtil.formatHttpDateString(dateTime));
                String time = DateUtil.format(DateUtil.formatHttpDateString(dateTime), "HH:mm:ss");
                String addr = arr[3];
                String gateway = arr[4];
                String parent = arr[5];
                String role = arr[6];
                String memoryused = arr[7];
                String ifin = arr[8];
                String ifout = arr[9];
                String local_stream = arr[10];
                LiveLogEntities entities = new LiveLogEntities();
                if (addr != null && addr != "") {
                    entities.setAddr(addr);
                }
                if (gateway != null && gateway != "") {
                    entities.setGateway(gateway);
                }
                if (parent != null && parent != "") {
                    entities.setParent(parent);
                }
                if (role != null && role != "") {
                    entities.setRole(role);
                }
                if (memoryused != null && memoryused != "") {
                    entities.setMemoryused(memoryused);
                }
                if (ifin != null && ifin != "") {
                    entities.setIfin(ifin);
                }
                if (ifout != null && ifout != "") {
                    entities.setIfout(ifout);
                }
                if (local_stream != null && local_stream != "") {
                    entities.setLocal_stream(local_stream);
                }
                entities.setCreateTime(date + " " + time);
                collector.emit(new Values(entities));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("livelogentity"));
    }
}
