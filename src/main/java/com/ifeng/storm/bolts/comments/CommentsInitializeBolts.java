package com.ifeng.storm.bolts.comments;

import com.ifeng.constant.CaculateStatTime;
import com.ifeng.entities.CommentsInfo;
import com.ifeng.redis.RedisClient;
import com.ifeng.redis.RedisFactory;
import org.apache.commons.lang.StringUtils;
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
 * Created by duanyb on 2016/12/23.
 */
public class CommentsInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;
    private RedisClient client;

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
            if (arr.length >= 4) {
                CommentsInfo commentsInfo = new CommentsInfo();
                String createTime = CaculateStatTime.caculte(arr[1].substring(0, 5).replace(":", ""), 5, 4);
                createTime = new StringBuilder().append(createTime.substring(0, 2)).append(":").append(createTime.substring(2, 4)).toString();
                commentsInfo.setCreateTime(createTime);
                commentsInfo.setCreateDate(arr[0]);
                String str = sentence.replaceAll("\\\\", "");
                Pattern pattern = Pattern.compile("\"doc_url\":\"(\\s?((http|ftp|https)://)([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]?)+([/[a-zA-Z0-9_-]]+.[a-zA-Z0-9_-]+))");
                Matcher m = pattern.matcher(str);
                boolean flag_Media = m.find();
                if (flag_Media) {
                    String mediaId_all = m.group(0).split("\"")[3];
                    String tmp[] = mediaId_all.split("/");
                    String media = tmp[tmp.length - 1];
                    mediaId_all = StringUtils.strip(mediaId_all);
                    if (mediaId_all.startsWith("http://cdn.iclient.ifeng.com")) {
                        commentsInfo.setMediaId(media.split("\\.")[0]);
                        commentsInfo.setType("newsapp");
                    } else if (mediaId_all.startsWith("http://i")) {
                        commentsInfo.setMediaId(tmp[tmp.length - 2]);
                        commentsInfo.setType("wap");
                    } else {

                        String st = media.split("\\.")[0];
                        commentsInfo.setMediaId(st.split("_")[0]);
                        commentsInfo.setType("wep");

                        Pattern pattern2 = Pattern.compile("\"rt\":\"[\\d|\\w|-]+");
                        Matcher m2 = pattern2.matcher(str);

                        boolean flag_Media2 = m2.find();
                        if (flag_Media2) {
                            if (m2.group(0).contains("sj")) {
                                st = media.split("\\.")[0];
                                commentsInfo.setMediaId(st.split("_")[0]);
                                commentsInfo.setType("wap");
                            }
                        }else {

                        }
                    }
                    collector.emit(new Values(commentsInfo));
                } else {
                    Pattern pattern1 = Pattern.compile("\"doc_url\":\"[\\d|\\w|-]+");
                    Matcher m1 = pattern1.matcher(str);
                    boolean flag_Media1 = m1.find();
                    if (flag_Media1) {
                        String mediaId_all = m1.group(0).split("\"")[3];

                        commentsInfo.setMediaId(mediaId_all);
                        commentsInfo.setType("media");

                        Pattern pattern2 = Pattern.compile("\"rt\":\"[\\d|\\w|-]+");
                        Matcher m2 = pattern2.matcher(str);
                        boolean flag_Media2 = m2.find();

                        if (flag_Media2) {
                            if (m2.group(0).contains("client_v")) {
                                commentsInfo.setMediaId(mediaId_all);
                                commentsInfo.setType("vapp");
                            }
                        }
                        collector.emit(new Values(commentsInfo));
                    }
                }
            }
        } catch (Exception er) {
            er.printStackTrace();
        } finally {
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("commentsInfo"));
    }
}
