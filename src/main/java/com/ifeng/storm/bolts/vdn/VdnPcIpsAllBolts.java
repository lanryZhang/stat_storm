package com.ifeng.storm.bolts.vdn;


import com.ifeng.constant.FieldsCombination;
import com.ifeng.core.misc.Area;
import com.ifeng.entities.vdn.VdnEntity;
import com.ifeng.entities.vdn.VdnInfo;
import com.ifeng.utils.Error_vdn;
import com.ifeng.utils.IpSearch;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换运营商 省份  等
 * Created by duanyb on 2016/10/19.
 */
public class VdnPcIpsAllBolts extends BaseRichBolt {
    private TopologyContext topologyContext;
    private OutputCollector outputCollector;
    private List<List<String>> cols = new ArrayList<List<String>>();
    private IpSearch ipSearch;
    private Map<String, Field> vdnMap = new HashMap<String, Field>();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.topologyContext = topologyContext;
        this.outputCollector = outputCollector;
        cols = new FieldsCombination().getVdnPcCols();
        Field[] fields = VdnEntity.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            vdnMap.put(field.getName(), field);
        }
        ipSearch = new IpSearch();
        ipSearch.initIp();
    }

    @Override
    public void execute(Tuple tuple) {
        VdnInfo vdnInfo = (VdnInfo) tuple.getValue(0);
        try {
            VdnEntity vdnEntity = new VdnEntity();
            if (vdnInfo != null) {
                vdnEntity.setFrm(vdnInfo.getFrm());
                vdnEntity.setTm(vdnInfo.getTm());
                vdnEntity.setTr(vdnInfo.getTr());
                vdnEntity.setStatistype("vv");
                String ip = vdnInfo.getIp();
                if (ip != null) {
                    IpSearch.Range range = ipSearch.getRange(ip);
                    if (range != null) {
                        Area area = (Area) range.getParam();
                        if (area != null) {
                            vdnEntity.setCity(area.getProvince());
                            vdnEntity.setNetName(area.getNetName());
                        }
                    }
                } else {
                    vdnEntity.setCity("other");
                    vdnEntity.setNetName("other");
                }
                vdnEntity.setDelkey(new Date());
                vdnEntity.setDateTime(getDateTime(vdnInfo.getTm() + " " + vdnInfo.getTr()));
                String erro = vdnInfo.getErr();
                if (erro != null) {
                    String codeName = Error_vdn.getName(erro);
                    if (codeName != null) {
                        Field f = vdnMap.get(codeName);
                        f.set(vdnEntity, 1);
                    }
                }
                String chenty = vdnInfo.getUrl();
                if (chenty != null) {
                    Pattern pattern = Pattern.compile("[_]+\\w{1}+[_]+\\w");
                    Matcher matcher = pattern.matcher(chenty);
                    if (matcher.find()) {
                        vdnEntity.setChentype("channel");
                    } else {
                        vdnEntity.setChentype("mysf");
                    }
                }
                String ptype = vdnInfo.getPtype();
                if (ptype != null) {
                    vdnEntity.setVideotype(vdnInfo.getPtype());
                }

                for (List<String> item : cols) {
                    VdnEntity t = new VdnEntity();
                    for (String inner : item) {
                        if (vdnMap.containsKey(inner)) {
                            Field f = vdnMap.get(inner);
                            f.set(t, f.get(vdnEntity));
                        }
                    }
                    t.setTm(vdnEntity.getTm());
                    t.setTr(vdnEntity.getTr());
                    t.setDelkey(vdnEntity.getDelkey());
                    t.setDateTime(vdnEntity.getDateTime());
                    outputCollector.emit(new Values(t, StringUtils.join(item, "_")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputCollector.ack(tuple);
    }

    private String getDateTime(String s) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
        Date date = dateFormat.parse(s);
        SimpleDateFormat dateFormat_ = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat_.format(date);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("VdnPc_ips", "vdnColsname"));
    }
}
