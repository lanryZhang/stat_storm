package com.ifeng.storm.bolts.ipserver;

import com.ifeng.entities.IpsEntity;
import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class IpsLogInitializeBolts extends BaseRichBolt {

    private OutputCollector collector;
    private TopologyContext context;
    private static final Logger logger = Logger.getLogger(IpsLogInitializeBolts.class);
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.context = topologyContext;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String sentence = tuple.getString(0);
            String[] arr = sentence.split(" ");
            String reg = ".* Redirect Service.*";
            String live3GReg = ".* 3GRedirect Service.*";
            String liveReg = ".* LiveAllocation Service.*";
            String hm = "00:00";
            IpsEntity en = new IpsEntity();
            if (arr.length > 25) {
                hm = arr[4].substring(0, arr[4].lastIndexOf(":"));
                en.setHm(hm);
                en.setCreateDate(arr[3].substring(1,arr[3].length()));
                en.setHostIp(arr[1]);
                if (sentence.matches(reg)) {
                    en.setRequestType("0");
                } else if (sentence.matches(liveReg)) {
                    en.setRequestType("1");
                } else if (sentence.matches(live3GReg)) {
                    en.setRequestType("2");
                }
                en.setNodeIp(arr[15]);
                if(arr[17].equals("WINDOWS")){
                    en.setClientType("PC");
                }else if(arr[17].equals("UNKNOWN")){
                    en.setClientType("UNKNOWN");
                }else {
                    en.setClientType("PHONE");
                }
               // en.setIsOver(arr[34]);
                en.setDateTime(new StringBuilder().append(en.getCreateDate()).append(" ").append(en.getHm()).toString());
                collector.emit(tuple,new Values(en));
            }

        }catch (Exception err){
            logger.error(err);
        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("ipslog"));
    }

}
