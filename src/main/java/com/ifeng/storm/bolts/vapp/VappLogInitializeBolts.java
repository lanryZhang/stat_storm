package com.ifeng.storm.bolts.vapp;

import com.ifeng.constant.CaculateStatTime;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.entities.VappStatInfo;
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
 * Created by duanyb on 2016/11/16.
 */
public class VappLogInitializeBolts extends BaseRichBolt {

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
            if (arr.length > 14) {
                VappStatInfo vappStatInfo = new VappStatInfo();
                String createTime = CaculateStatTime.caculte(arr[1].substring(0, 4), 100, 4);
                createTime = new StringBuilder().append(createTime.substring(0, 2)).append(":").append(createTime.substring(2, 4)).toString();
                String createDate = arr[0];
                vappStatInfo.setCreateTime(createTime);
                vappStatInfo.setCreateDate(createDate);

//                Pattern pattern = Pattern.compile("uid=[\\d|\\w|-]+");
//                Matcher m = pattern.matcher(arr[11]);
//                boolean flag_uid = m.find();
//                if (flag_uid) {
                // vappStatInfo.setUid(m.group(0).split("=")[1]);
                vappStatInfo.setUid(arr[7]);
                // }
                if ("v".equals(arr[13].toLowerCase())) {
                    Pattern pattern_ = Pattern.compile("vid=[\\d|\\w|-]+");
                    Matcher m_ = pattern_.matcher(arr[14]);
                    String media = "";
                    if (m_.find()) {
                        media = m_.group(0).split("=")[1];
                        vappStatInfo.setVid(media);
                    }
                    vappStatInfo.setMediaId(media);
                    vappStatInfo.setPv(1);
                    vappStatInfo.setUv(1);
                    vappStatInfo.setVv(1);

                    PageStatInfo pageStatInfo_ = new PageStatInfo();
                    pageStatInfo_.setMediaId(media);
                    pageStatInfo_.setCreateTime(createTime);
                    pageStatInfo_.setCreateDate(arr[0]);
                    collector.emit(new Values(pageStatInfo_, vappStatInfo));
                }
                //  }


            }
        } catch (Exception er) {
            er.printStackTrace();
        } finally {
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("vappUVInfo", "vappStatInfo"));
    }
}
