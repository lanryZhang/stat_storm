package com.ifeng.storm.bolts.bandwidth;

import com.ifeng.entities.BandWidthEntity;
import com.ifeng.core.misc.Area;
import com.ifeng.core.misc.IpRange;
import com.ifeng.core.misc.IpV4Address;
import com.ifeng.utils.DateUtil;
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
public class BandWidthInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;
    private IpRange ipRange = new IpRange();
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        ipRange.initRangeSet();
    }

    @Override
    public void execute(Tuple input) {
        try{
            String sentence = input.getString(0);
            BandWidthEntity en = new BandWidthEntity();
            String regLive = "^GET /live-range.live.*";
            String regLive3G = "^GET /live/.*";
            Pattern patternLive =Pattern.compile(regLive);
            Pattern patternLive3G = Pattern.compile(regLive3G);
            String[] arr = sentence.split("\"");
            int status = Integer.parseInt(arr[2].trim().split(" ")[0]);
            if (status == 200 || status == 206 || status == 304) {
                String hostIp = arr[0].trim().split(" ")[0];
                Area area =null;
                if(!hostIp.equals("-")){
                    area = ipRange.getAreaByIp(new IpV4Address(hostIp));
                }
                if(area!=null){
                    en.setNetName(area.getNetName());
                }
                en.setHostIp(arr[0].trim().split(" ")[0]);
                String dateTime = arr[0].split(" ")[4].replace("[", "");
                String date = DateUtil.formatDate(DateUtil.formatHttpDateString(dateTime));
                String time = DateUtil.format(DateUtil.formatHttpDateString(dateTime), "HH:mm");
                en.setLocalTime(date+" "+time);
                String type = arr[1].trim();
                Matcher matcherLive = patternLive.matcher(type);
                Matcher matcherLive3G = patternLive3G.matcher(type);
                if(matcherLive.find()){
                    type = "PC直播";
                    en.setType("PC直播");
                }else if(matcherLive3G.find()){
                    type = "手机直播";
                    en.setType("手机直播");
                }else{
                    type = "点播";
                    en.setType("点播");
                }
                en.setBodyBytesSent(Long.parseLong(arr[6].trim()));
                collector.emit(new Values(en.clone(),Long.parseLong(arr[6].trim()),date+time+hostIp,"bandwidth"));
                collector.emit(new Values(en.clone(),Long.parseLong(arr[6].trim()),date+time+en.getNetName(),"bandwidth_netName"));
                collector.emit(new Values(en.clone(),Long.parseLong(arr[6].trim()),date+time+en.getNetName()+type,"bandwidth_netName_type"));
            }
        }catch (Exception er){
        }finally {
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logEntity","bandwith","dateTime","columnName"));
    }
}
