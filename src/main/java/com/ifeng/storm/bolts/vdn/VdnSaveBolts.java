package com.ifeng.storm.bolts.vdn;


import com.ifeng.entities.vdn.VdnEntity;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IpsLogFlush;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by duanyb on 2016/10/25.
 */
public class VdnSaveBolts extends BaseRichBolt {
    private FlushHashMap<VdnEntity, Integer> flushHashMap;
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
          this.collector = outputCollector;
        flushHashMap = new FlushHashMap<VdnEntity,Integer>();
        flushHashMap.setFlusher(new IpsLogFlush());
    }

    @Override
    public void execute(Tuple tuple) {
        VdnEntity vdnEntity = (VdnEntity) tuple.getValue(0);
        String colName = (String) tuple.getValue(1);

        flushHashMap.put(colName,vdnEntity,1);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
