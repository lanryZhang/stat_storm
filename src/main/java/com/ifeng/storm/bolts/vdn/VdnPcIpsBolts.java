package com.ifeng.storm.bolts.vdn;


import com.ifeng.entities.vdn.IpsInfo;
import com.ifeng.entities.vdn.VdnInfo;
import com.ifeng.utils.Cache;
import com.ifeng.utils.CacheManager;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 补数据 和 合并ips guid
 * <p>
 * Created by duanyb on 2016/10/18.
 */
public class VdnPcIpsBolts extends BaseRichBolt {
    private OutputCollector collector;
    private TopologyContext context;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.context = topologyContext;


    }

    @Override
    public void execute(Tuple tuple) {
        try {
            VdnInfo vdnInfo = (VdnInfo) tuple.getValue(0);
            IpsInfo ipsInfo = (IpsInfo) tuple.getValue(1);
            if (vdnInfo != null) {
                VdnInfo vdnkey = (VdnInfo) vdnInfo.clone();
                VdnInfo vdnkey_ = (VdnInfo) vdnInfo.clone_();
                Cache cache = CacheManager.getContent(vdnkey);
                Cache cache_guid = CacheManager.getContent(vdnInfo.getGuid());
                if (cache_guid != null) {
                    IpsInfo ipsInfo1 = (IpsInfo) cache_guid.getValue();
                    vdnInfo.setFrm(ipsInfo1.getReduurl());
                } else {
                    vdnInfo.setFrm("other");
                }
                collector.emit(new Values(vdnInfo));
                if (cache != null) {
                    ConcurrentHashMap<VdnInfo, List<String>> concurrentHashMap = (ConcurrentHashMap<VdnInfo, List<String>>) cache.getValue();
                    if (concurrentHashMap != null) {
                        List<String> errlist = concurrentHashMap.get(vdnkey_);
                        if (errlist != null && errlist.size() > 0) {
                            errlist.add(vdnInfo.getErr());
                            cache.setValue(concurrentHashMap.put(vdnkey_, errlist));
                            CacheManager.putCache(vdnkey, cache);

                            if (vdnInfo.getErr().equals("303000")) {
                                if (!errlist.contains("208000")) {
                                    for (Map.Entry<VdnInfo, List<String>> obj : concurrentHashMap.entrySet()) {
                                        if (obj.getValue().contains("208000")) {
                                            VdnInfo vdnInfo1 = vdnInfo;
                                            vdnInfo1.setErr("208000");
                                            collector.emit(new Values(vdnInfo1));
                                            break;
                                        }
                                    }
                                }
                            } else if (vdnInfo.getErr().equals("304001")) {
                                if (!errlist.contains("303000")) {
                                    for (Map.Entry<VdnInfo, List<String>> obj : concurrentHashMap.entrySet()) {
                                        if (obj.getValue().contains("303000")) {
                                            VdnInfo vdnInfo1 = vdnInfo;
                                            vdnInfo1.setErr("303000");
                                            collector.emit(new Values(vdnInfo1));
                                            break;
                                        }
                                    }
                                }
                            } else if (vdnInfo.getErr().equals("304002")) {
                                if (!errlist.contains("303001")) {
                                    for (Map.Entry<VdnInfo, List<String>> obj : concurrentHashMap.entrySet()) {
                                        if (obj.getValue().contains("303001")) {
                                            VdnInfo vdnInfo1 = vdnInfo;
                                            vdnInfo1.setErr("303001");
                                            collector.emit(new Values(vdnInfo1));
                                            break;
                                        }
                                    }
                                }
                            } else if (vdnInfo.getErr().equals("304003")) {
                                if (!errlist.contains("303002")) {
                                    for (Map.Entry<VdnInfo, List<String>> obj : concurrentHashMap.entrySet()) {
                                        if (obj.getValue().contains("303002")) {
                                            VdnInfo vdnInfo1 = vdnInfo;
                                            vdnInfo1.setErr("303002");
                                            collector.emit(new Values(vdnInfo1));
                                            break;
                                        }
                                    }
                                }
                            } else if (vdnInfo.getErr().compareTo("304004") >= 0) {
                                if (!errlist.contains("303003")) {
                                    for (Map.Entry<VdnInfo, List<String>> obj : concurrentHashMap.entrySet()) {
                                        if (obj.getValue().contains("303003")) {
                                            VdnInfo vdnInfo1 = vdnInfo;
                                            vdnInfo1.setErr("303003");
                                            collector.emit(new Values(vdnInfo1));
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            errlist = new ArrayList<String>();
                            errlist.add(vdnInfo.getErr());
                            cache.setValue(concurrentHashMap.put(vdnkey_, errlist));
                            CacheManager.putCache(vdnkey, cache);
                        }
                    }
                } else {
                    Cache cache_ = new Cache();
                    ConcurrentHashMap<VdnInfo, List<String>> map = new ConcurrentHashMap<VdnInfo, List<String>>();
                    List<String> list = new ArrayList<String>();
                    list.add(vdnInfo.getErr());
                    map.put(vdnkey_, list);
                    cache.setValue(map);
                    CacheManager.putContent(vdnkey, cache_, 1000 * 60 * 10);
                }
            }
            if (ipsInfo != null) {
                Cache cache_ = new Cache();
                cache_.setTimeOut(1000 * 60 * 10);
                cache_.setValue(ipsInfo);
                CacheManager.putCache(ipsInfo.getGuid(), cache_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        collector.ack(tuple);
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("vdn_ips"));
    }
}
