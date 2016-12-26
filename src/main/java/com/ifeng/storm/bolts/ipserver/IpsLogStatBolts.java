package com.ifeng.storm.bolts.ipserver;

import com.ifeng.entities.IpsEntity;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IpsLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class IpsLogStatBolts extends BaseRichBolt {
    private FlushHashMap<IpsEntity,Integer> flushHashMap;
    private OutputCollector collector;
    @Override
    public void cleanup() {

    }

    @Override
    public void execute(Tuple tuple) {
        try {

            IpsEntity en = (IpsEntity) tuple.getValue(0);
            String colName = (String) tuple.getValue(1);

            //System.err.println("执行了："+this.hashCode() +" "+colName + ":" + en.toString());
            flushHashMap.put(colName,en,1);
        } catch (Exception err) {

        }finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        flushHashMap = new FlushHashMap<>();
        flushHashMap.setFlusher(new IpsLogFlush());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
