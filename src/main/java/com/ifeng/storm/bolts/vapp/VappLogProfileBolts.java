package com.ifeng.storm.bolts.vapp;

import com.ifeng.constant.FieldsCombination;
import com.ifeng.entities.VappStatInfo;
import com.ifeng.entities.ZmtDescription;
import com.ifeng.entities.ZmtDescriptionCounter;
import com.ifeng.persistence.FlushHashMap;
import com.ifeng.persistence.IFlush;
import com.ifeng.persistence.ThresholdHashMap;
import com.ifeng.persistence.profile.UserProfileKafkaFlush;
import com.ifeng.redis.RedisClient;
import com.ifeng.redis.RedisFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by duanyb on 2016/12/24.
 */
public class VappLogProfileBolts extends BaseRichBolt {
    private FlushHashMap<ZmtDescription, ZmtDescriptionCounter> flushHashMap;
    private List<List<String>> cols = new ArrayList<>();
    private Map<String, Field> fieldMap = new HashMap<>();
    private OutputCollector collector;
    private TopologyContext context;
    private RedisClient client, clientup;
    private ThresholdHashMap thresholdHashMap;
    private final static Logger logger = Logger.getLogger(VappLogsaveBolts.class);

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        cols = new FieldsCombination().getProfileCols();
        Field[] fields = ZmtDescription.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        this.collector = outputCollector;
        context = topologyContext;
        flushHashMap = new FlushHashMap<>(60 * 20);
        IFlush iFlush = new UserProfileKafkaFlush(map.get("kafkaTopicProfile").toString());
        flushHashMap.setFlusher(iFlush);
        clientup = RedisFactory.newInstance("up", true);
        client = RedisFactory.newInstance();
        thresholdHashMap = new ThresholdHashMap(500L, 60 * 60);
    }

    @Override
    public void execute(Tuple tuple) {
        VappStatInfo en_vapp = (VappStatInfo) tuple.getValue(1);
        try {
            ZmtDescription zmtDescription = new ZmtDescription();
            zmtDescription.setPgcId(en_vapp.getPgcId());
            zmtDescription.setCreateDate(en_vapp.getCreateDate());
            zmtDescription.setCreateTime(en_vapp.getCreateTime());

            if (en_vapp.getVid() != "") {
                String pgcId = client.getString(en_vapp.getVid());
                if (pgcId != null) {
                    zmtDescription.setPgcId(pgcId.replace("\"", ""));
                    String tableName = StringUtils.join(cols.get(0), "_");
                    ZmtDescriptionCounter zmtDescriptionCounter = new ZmtDescriptionCounter();
                    if (!thresholdHashMap.incr(zmtDescription.getPgcId())) {
                        int result = new Random().nextInt(100);
                        if (result >= 19) {
                            return;
                        }
                    }
                    List<String> str = clientup.hmget(en_vapp.getUid(), "pre_gender", "loc");
                    boolean flushFlag = false;
                    if (str.get(0) != null) {
                        if (str.get(0).contains("male")) {
                            zmtDescriptionCounter.getGender().put("male", 1);
                        } else {
                            zmtDescriptionCounter.getGender().put("female", 1);
                        }
                        flushFlag = true;
                    }

                    if (str.get(1) != null) {
                        int pr = str.get(1).lastIndexOf("_");
                        if (pr > 0) {
                            zmtDescriptionCounter.getAreas().put(str.get(1).substring(0, pr), 1);
                            zmtDescriptionCounter.getCities().put(str.get(1).substring(pr + 1), 1);
                        }
                        flushFlag = true;
                    }
                    if(flushFlag) {
                        flushHashMap.put(tableName, zmtDescription, zmtDescriptionCounter);
                    }
                }
            } else {
                logger.error("can not find pgcid " + en_vapp.getVid());
            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            collector.ack(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("vappPgcIdInfo"));
    }
}
