package com.ifeng.storm.bolts.pagestat;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class WapNewsLogInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;
    //private RedisClient client ;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        //client = RedisFactory.newInstance();
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] arr = sentence.split("\\s+");
        try {
            if (arr.length > 10) {
                PageStatInfo pageStatInfo = new PageStatInfo();

                pageStatInfo.setUrl(arr[4]);
                //根据Url查询自媒体信息
                //String value = client.getString(arr[1]);
                String ci = arr[18];

                Pattern pattern = Pattern.compile("fhh_[\\d|\\w|-]+");
                Matcher m = pattern.matcher(ci);
                boolean flag_Media = m.find();
                if (flag_Media) {
                    String t = m.group(0);
                    pageStatInfo.setMediaId(t.substring(t.lastIndexOf("_")+1,t.length()));
                }
                Pattern pattern_ = Pattern.compile("zmt_[\\d|\\w|-]+");
                Matcher m_ = pattern_.matcher(ci);
                boolean flag_pgcId = m_.find();
                if (flag_pgcId) {
                    String t = m_.group(0);
                    pageStatInfo.setPgcId(t.substring(t.lastIndexOf("_")+1,t.length()));
                }
                if (flag_Media && flag_pgcId) {
                    pageStatInfo.setType("page");
                    String createTime = CaculateStatTime.caculte(arr[1].substring(0,4),5,4);
                    createTime = new StringBuilder().append(createTime.substring(0,2)).append(":").append(createTime.substring(2,4)).toString();
                    pageStatInfo.setCreateTime(createTime);
                    pageStatInfo.setCreateDate(arr[0]);
                    pageStatInfo.setUid(arr[4]);

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
        declarer.declare(new Fields("pageStatInfo"));
    }
}
