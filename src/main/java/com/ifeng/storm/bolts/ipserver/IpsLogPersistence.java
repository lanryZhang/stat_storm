package com.ifeng.storm.bolts.ipserver;


import com.ifeng.entities.IpsEntity;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by zhanglr on 2016/4/5.
 */
public class IpsLogPersistence extends BaseRichBolt{
    private OutputCollector collector;
    private TopologyContext context;
    private String collectionName;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        collectionName = input.getString(2);
        IpsEntity en = (IpsEntity) input.getValue(0);
        int count = input.getInteger(1);
        en.setRequestNum(count);
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
