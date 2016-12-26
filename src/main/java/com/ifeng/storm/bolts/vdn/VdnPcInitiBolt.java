package com.ifeng.storm.bolts.vdn;

import com.ifeng.entities.vdn.IpsInfo;
import com.ifeng.entities.vdn.VdnInfo;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duanyb on 2016/10/14.
 */
public class VdnPcInitiBolt extends BaseRichBolt {

    private TopologyContext topologyContext;
    private OutputCollector outputCollector;


    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.topologyContext = topologyContext;
        this.outputCollector = outputCollector;

    }

    @Override
    public void execute(Tuple tuple) {
        try {
            VdnInfo vdnInfo = new VdnInfo();
            IpsInfo ipsinfo = new IpsInfo();
            String line = tuple.getString(0);
            if (line.contains("NioProcessor") && line.split(" ").length > 36) {
                String tarray[] = line.split(" ");
                Pattern pattern = Pattern.compile("(\\d{2}[:]\\d{2}[:]\\d{2})");
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    String time = m.group();
                    if (time != null && time.split(":").length >= 1) {
                        ipsinfo.setTr(getTmTr(time.split(":")[0] + time.split(":")[1])[0]);
                        ipsinfo.setTm(getTmTr(time.split(":")[0] + time.split(":")[1])[0]);
                    }
                }
                String gid = "nul";
                String http = "nul";
                if (line.contains("Redirect Service") || line.contains("3GRedirect Service")) {
                    gid = tarray[36];
                    String tmp[] = tarray[20].split("/");
                    http = tmp[2];
                    if (http.indexOf(":") != -1) {
                        http = http.substring(0, http.indexOf(":"));
                    }
                } else if (line.contains("LiveAllocation Service")) {
                    String tmp[] = tarray[22].split("/");
                    http = tmp[2];
                    if (http.indexOf(":") != -1) {
                        http = http.substring(0, http.indexOf(":"));
                    }
                    gid = line.split(" ")[40];
                }
                ipsinfo.setGuid(gid);
                ipsinfo.setReduurl(http);
            } else if (line.split("\t").length >= 25) {
                String lineArray[] = line.split("\t");
                if (lineArray[15] != null) {
                    String errcode = lineArray[15];
                    if (errcode.contains("30400") && errcode.compareTo("304004") >= 0) {
                        errcode = "304004";
                    } else if (errcode.contains("301030")) {
                        errcode = "301030";
                    }
                    if (isCheck(errcode)) {
                        vdnInfo.setGuid(errcode);
                        Pattern pattern = Pattern.compile("(\\d{2}\\d{2}.sta.gz)");
                        Matcher m = pattern.matcher(line);
                        if (m.find()) {
                            String time = m.group();
                            if (time != null && time.split(".").length >= 1) {
                                vdnInfo.setTr(getTmTr(time.split(".")[0])[1]);
                                vdnInfo.setTm(getTmTr(time.split(".")[0])[0]);
                            }
                        }
                        vdnInfo.setErr(lineArray[15]);
                        vdnInfo.setIp(lineArray[2]);

                        vdnInfo.setPtype(lineArray[20]);

                        vdnInfo.setUid(lineArray[5]);
                        vdnInfo.setUrl(lineArray[3]);
                        try {
                            if (lineArray[12] != "#") {
                                String url = URLDecoder.decode(lineArray[12], "utf-8");
                                Pattern pattern_ = Pattern.compile("([A-Za-z0-9_]+[.][A-Za-z0-9_]+[.][A-Za-z0-9_]+[[.[0-9_]]{1,3}]*)");
                                Matcher m_ = pattern_.matcher(url);
                                if (m_.find()) {
                                    String frm = m_.group();
                                    vdnInfo.setFrm(frm);
                                }
                            } else {
                                vdnInfo.setFrm("nul");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            vdnInfo.setFrm("nul");
                        }
                    }
                }
            }
            if (vdnInfo.getErr() == null && ipsinfo.getGuid() == null) {

            } else {
                outputCollector.emit(new Values(vdnInfo, ipsinfo));
            }
        } catch (Exception err) {
        }finally {
            outputCollector.ack(tuple);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("vdn_pc", "ips_"));
    }

    private String getNowDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
    }

    private String[] getTmTr(String tr) throws ParseException {
        String[] tmtr = new String[2];
        String tm = "";
        int tr_ = Integer.parseInt(tr.substring(2));
        if (tr_ % 2 == 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
            Date date = simpleDateFormat.parse(tr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
            tmtr[1] = simpleDateFormat.format(calendar.getTime());
            if (tmtr[1].equals("0000")) {
                calendar.setTime(new Date());
                calendar.set(calendar.DATE, calendar.get(calendar.DATE) + 1);
                tm = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            } else {
                tm = getNowDate();
            }
            tmtr[0] = tm;
        } else {
            tmtr[1] = tr;
            tmtr[0] = getNowDate();
        }
        return tmtr;

    }

    public boolean isCheck(String str) {
        switch (str) {
            case "208000":
                return true;
            case "304001":
                return true;
            case "304002":
                return true;
            case "304003":
                return true;
            case "304004":
                return true;
            case "301010":
                return true;
            case "301020":
                return true;
            case "301030":
                return true;
            case "100000":
                return true;
            case "303000":
                return true;
            case "110000":
                return true;
            case "301040":
                return true;
            case "601000":
                return true;
            case "602000":
                return true;
            default:
                return false;
        }
        
    }
}
