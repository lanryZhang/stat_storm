package com.ifeng.storm.bolts.media;

import com.ifeng.constant.CaculateStatTime;
import com.ifeng.entities.PageStatInfo;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by duanyb on 2016/11/18.
 */
public class MediaLogInitializeBolts extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] arr = sentence.split("\\s+");
        try {
            if (arr.length > 15) {
                PageStatInfo pageStatInfo = new PageStatInfo();
                String createTime = CaculateStatTime.caculte(arr[1].substring(0,4),5,4);
                createTime = new StringBuilder().append(createTime.substring(0,2)).append(":").append(createTime.substring(2,4)).toString();

                String createDate = arr[0];
                String url = arr[5];
                pageStatInfo.setUrl(url);
                pageStatInfo.setCreateTime(createTime);
                pageStatInfo.setCreateDate(createDate);
                pageStatInfo.setMediaId(arr[2]);
                pageStatInfo.setUid(arr[7]);

                String  vv = arr[14];
                if("vv".equals(vv.toLowerCase())){
                    pageStatInfo.setPv(1);
                    pageStatInfo.setVv(1);
                    pageStatInfo.setUv(1);
                    collector.emit(new Values(pageStatInfo));
                }
            }
        } catch (Exception er) {
            er.printStackTrace();
        }finally {
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("mediaLogInfo"));
    }
}

