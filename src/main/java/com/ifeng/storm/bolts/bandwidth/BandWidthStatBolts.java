package com.ifeng.storm.bolts.bandwidth;

import com.ifeng.entities.BandWidthEntity;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.NginxLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class BandWidthStatBolts extends BaseRichBolt {
    private FlushHashMap<String,BandWidthEntity> flushHashMap;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        flushHashMap = new FlushHashMap();
        flushHashMap.setFlusher(new NginxLogFlush("nginx_stat"));
    }

    @Override
    public void execute(Tuple input) {
        BandWidthEntity en = (BandWidthEntity) input.getValue(0);
        long bytes = input.getLong(1);
        String columName = (String) input.getValue(3);
        String key = (String) input.getValue(2);
        try {
            //System.err.println("执行了："+this.hashCode() +" "+ en.toString());
         //   flushHashMap.put("bandwidth", en, bytes);
            flushHashMap.put(columName, key, en);
        } catch (Exception err) {
            collector.fail(input);
        }finally {
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logEntity","bandwith"));
    }
}
