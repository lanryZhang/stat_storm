package com.ifeng.storm.bolts.pagestat;

import com.ifeng.constant.CaculateStatTime;
import com.ifeng.entities.PageStatInfo;
import com.ifeng.redis.RedisClient;
import com.ifeng.redis.RedisFactory;
import com.ifeng.utils.MD5Util;
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
public class NewsAppLogInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;

    private RedisClient client ;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        client = RedisFactory.newInstance();
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] arr = sentence.split("\\s+");
        try {
            if (arr.length > 14) {
                PageStatInfo pageStatInfo = new PageStatInfo();
                String createTime = CaculateStatTime.caculte(arr[1].substring(0,4),5,4);
                createTime = new StringBuilder().append(createTime.substring(0,2)).append(":").append(createTime.substring(2,4)).toString();
                pageStatInfo.setCreateTime(createTime);
                pageStatInfo.setCreateDate(arr[0]);
                //根据Url查询自媒体信息
                //String value = client.getString(arr[1]);
                String type = arr[13];
                if ("page".equals(type.toLowerCase())) {
                    pageStatInfo.setPv(1);
                    pageStatInfo.setType("page");
                    Pattern pattern = Pattern.compile("sub_[\\d|\\w|-]+");
                    Matcher m = pattern.matcher(arr[14]);
                    boolean flag_Media = m.find();
                    if (flag_Media) {
                        String t = m.group(0);
                        pageStatInfo.setMediaId(t.substring(t.lastIndexOf("_")+1,t.length()));
                        String md5 = MD5Util.md5(pageStatInfo.getMediaId() + "~!@#$%^&*()_+{}|\":>?<");
                        String dir1 = md5.substring(0,3);
                        String dir2 = md5.substring(3,6);
                        String dir3 = md5.substring(6,9);
                        pageStatInfo.setUrl("http://cdn.iclient.ifeng.com/res/article/"+dir1+"/"+dir2+"/"+dir3+"/"+pageStatInfo.getMediaId()+".html");
                    }

                    Pattern pattern_ = Pattern.compile("src=[\\d|\\w|-]+");
                    Matcher m_ = pattern_.matcher(arr[14]);
                    boolean flag_pgcId = m_.find();
                    if (flag_pgcId) {
                        pageStatInfo.setPgcId(m_.group(0).split("=")[1]);
                    }
                    if (flag_Media && flag_pgcId) {
                         pageStatInfo.setUid(arr[7]);
                        collector.emit(new Values(pageStatInfo));
                    }
                }else if("v".equals(type.toLowerCase())){
                    pageStatInfo.setType("video");
                    Pattern pattern = Pattern.compile("vid=(video_){0,3}[\\d|\\w|-]+");
                    Matcher m = pattern.matcher(arr[14]);
                    boolean vid = m.find();
                    if(vid){
                        pageStatInfo.setUid(arr[7]);
                        String t = m.group(0);
                        pageStatInfo.setMediaId(t.substring(t.lastIndexOf("_")+1,t.length()));
                        collector.emit(new Values(pageStatInfo));
                    }
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
