package com.ifeng.storm.bolts.p2p;

import com.ifeng.entities.*;
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

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class P2PInitializeBolts extends BaseRichBolt {
    private OutputCollector collector;
    private IpRange ipRange = new IpRange();
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        ipRange.initRangeSet();
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] arr = sentence.split(" ");
        try {
            if (arr.length > 64) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String url = arr[3].trim();
                String dateTime = arr[1].replace("[", "").trim();
                String remoteIp = arr[0];
                Area area =null;
                if(!remoteIp.equals("-")){
                    area = ipRange.getAreaByIp(new IpV4Address(remoteIp));
                }
                String date = DateUtil.formatDate(DateUtil.formatHttpDateString(dateTime));
                String time = DateUtil.format(DateUtil.formatHttpDateString(dateTime), "HH:mm");
                String type = arr[6];
                 String zvReg =  ".*zv\\.3gv\\.ifeng\\.com.*";
                String recvRes = arr[20];
                String sendRes = arr[26];
                String pInitialStatus = arr[32];
                String peerCount = arr[57];
                String heat = arr[59];
                String duration = arr[63];
                String nat = arr[64];
                PeerLogEntity peerLogEntity = null;
                PeerInfo pinfo= null;
                P2PAcceptEntity p2PAcceptEntity= null;
                P2PSendEntity p2PSendEntity= null;
                P2PSingleEntity p2PSingleEntity = null;
                P2PServerCountEntity p2PServerCountEntity = null;
                P2PServerProgramTopEntity p2PServerProgramTopEntity = null;
                P2PServerDurationEntity p2PServerDurationEntity = null;
                P2PNatEntity p2PNatEntity = null;

                if (url != null && url.length() > 10
                        && !type.equals("new")) {
                    peerLogEntity = new PeerLogEntity();
                    if (url.toLowerCase().matches(zvReg)){
                        peerLogEntity.setRequestType("1");
                    }else {
                        peerLogEntity.setRequestType("0");
                    }
                    peerLogEntity.setDate(date);
                    peerLogEntity.setTime(time);
                    peerLogEntity.setDateTime(peerLogEntity.getDate() + " " + peerLogEntity.getTime());
                    if (area != null) {
                        peerLogEntity.setNetName(area.getNetName());
                    }
                    peerLogEntity.setGroups("test");

                    pinfo = new PeerInfo();
                    if (type.toLowerCase().equals("p2p")){
                        pinfo.setP2p_DlBytes(castToLong(arr[10]));
                        if(arr[7].equals("success")){
                            pinfo.setP2pDoneCount(1);
                        }else{
                            pinfo.setP2pDoneCount(0);
                        }
                    } else if (type.toLowerCase().equals("cdn")){
                        if(arr[7].equals("success")){
                            pinfo.setCdnDoneCount(1);
                        }else{
                            pinfo.setCdnDoneCount(0);
                        }
                        pinfo.setCdn_DlBytes(castToLong(arr[10]));
                    }
                    pinfo.setP2pSendCount(castToLong(arr[14]));
                    pinfo.setP2pSendCountPre(castToLong(arr[15]));
                    //collector.emit(new Values(peerLogEntity, pinfo));
                }
                //P2P
                if(!arr[20].equals("-")||!arr[21].equals("-")||!arr[22].equals("-")||!arr[23].equals("-")||!arr[24].equals("-")||!arr[25].equals("-")){
                    p2PAcceptEntity = new P2PAcceptEntity();
                    p2PAcceptEntity.setInquiryStatus(java.net.URLDecoder.decode(arr[17], "utf-8"));
                    p2PAcceptEntity.setTrackServerInfo(java.net.URLDecoder.decode(arr[18],"utf-8"));
                    p2PAcceptEntity.setInitialStatus(java.net.URLDecoder.decode(arr[19], "utf-8"));
                    p2PAcceptEntity.setGuid(java.net.URLDecoder.decode(arr[4], "utf-8"));
                    p2PAcceptEntity.setAvailableRecvRes(java.net.URLDecoder.decode(arr[20], "utf-8"));
                    p2PAcceptEntity.setKnockDoor(java.net.URLDecoder.decode(arr[21], "utf-8"));
                    p2PAcceptEntity.setRecvSpeed(java.net.URLDecoder.decode(arr[22], "utf-8"));
                    p2PAcceptEntity.setRecvTime(java.net.URLDecoder.decode(arr[23], "utf-8"));
                    p2PAcceptEntity.setDemandPeerStatus(java.net.URLDecoder.decode(arr[24], "utf-8"));
                    p2PAcceptEntity.setSupplyNum(castToInt(arr[25]));
                    p2PAcceptEntity.setDate(date);
                    p2PAcceptEntity.setTime(time);
                    p2PAcceptEntity.setDateTime(date + " " + time);
                }
                //P2P
                if(!arr[26].equals("-")||!arr[27].equals("-")||!arr[28].equals("-")||!arr[29].equals("-")||!arr[30].equals("-")||!arr[31].equals("-")){
                    p2PSendEntity = new P2PSendEntity();
                    p2PSendEntity.setInquiryStatus(java.net.URLDecoder.decode(arr[17],"utf-8"));
                    p2PSendEntity.setTrackServerInfo(java.net.URLDecoder.decode(arr[18],"utf-8"));
                    p2PSendEntity.setInitialStatus(java.net.URLDecoder.decode(arr[19], "utf-8"));
                    p2PSendEntity.setGuid(java.net.URLDecoder.decode(arr[4], "utf-8"));
                    p2PSendEntity.setSendRes(java.net.URLDecoder.decode(arr[26], "utf-8"));
                    p2PSendEntity.setSendData(java.net.URLDecoder.decode(arr[27], "utf-8"));
                    p2PSendEntity.setSendSpeed(java.net.URLDecoder.decode(arr[28], "utf-8"));
                    p2PSendEntity.setSendTime(java.net.URLDecoder.decode(arr[29], "utf-8"));
                    p2PSendEntity.setSupplyPeerStatus(java.net.URLDecoder.decode(arr[30], "utf-8"));
                    p2PSendEntity.setSendTargetRewrite(java.net.URLDecoder.decode(arr[31], "utf-8"));
                    p2PSendEntity.setDate(date);
                    p2PSendEntity.setTime(time);
                    p2PSendEntity.setDateTime(date + " " + time);
                }
                //p2p single
                if(pInitialStatus!=null&&!pInitialStatus.equals("-")){
                    p2PSingleEntity = new P2PSingleEntity();
                    p2PSingleEntity.setpInitialStatus(castToLong(arr[32]));
                    p2PSingleEntity.setGuid(java.net.URLDecoder.decode(arr[4], "utf-8"));
                    p2PSingleEntity.setdWantRequestSum(castToLong(arr[33]));
                    p2PSingleEntity.setdWantResponseSum(castToLong(arr[34]));
                    p2PSingleEntity.setdKnockDoorSum(castToLong(arr[35]));
                    p2PSingleEntity.setdRecvFirstDataSum(castToLong(arr[36]));
                    p2PSingleEntity.setsSharedRequestSum(castToLong(arr[37]));
                    p2PSingleEntity.setsLetDataGoSum(castToLong(arr[38]));
                    p2PSingleEntity.setsRecvAckSum(castToLong(arr[39]));
                    p2PSingleEntity.setpInquiryConnectSum(castToLong(arr[40]));
                    p2PSingleEntity.setpInquiryResponseStatus(castToLong(arr[41]));
                    p2PSingleEntity.setpTsConnectSum(castToLong(arr[42]));
                    p2PSingleEntity.setpTsResponseStatus(castToLong(arr[43]));
                    p2PSingleEntity.setpTcpShakeSuccessSum(castToLong(arr[44]));
                    p2PSingleEntity.setpTcpShakeFailSum(castToLong(arr[45]));
                    p2PSingleEntity.setDate(date);
                    p2PSingleEntity.setTime(time);
                    p2PSingleEntity.setDateTime(date + " " + time);
                    p2PSingleEntity.setdWantFrequestSum(castToLong(arr[46]));
                    p2PSingleEntity.setdWantFresponseSum(castToLong(arr[47]));
                    p2PSingleEntity.setdAskResWithoutP2p(castToLong(arr[48]));
                    p2PSingleEntity.setsShareSum(castToLong(arr[49]));
                    p2PSingleEntity.setPlLcnt(castToLong(arr[50]));
                    p2PSingleEntity.setPlVcnt(castToLong(arr[51]));
                    p2PSingleEntity.setP2pDlB(castToLong(arr[52]));
                    p2PSingleEntity.setCdnDlB(castToLong(arr[53]));
                    p2PSingleEntity.setdKdRecvFirstDataSum(castToLong(arr[54]));

                }
                //p2p server count
                if(peerCount!=null&&!peerCount.equals("-")){
                    p2PServerCountEntity = new P2PServerCountEntity();
                    p2PServerCountEntity.setDate(date);
                    p2PServerCountEntity.setTime(time);
                    if(arr[55].equals("1")){
                        p2PServerCountEntity.setNetName("联通");
                    }else if(arr[55].equals("2")){
                        p2PServerCountEntity.setNetName("移动");
                    }else if(arr[55].equals("3")){
                        p2PServerCountEntity.setNetName("电信");
                    }
                    Long dt=new Long(arr[56]+"000");
                    p2PServerCountEntity.setDateTime(sdf.format(dt));
                    p2PServerCountEntity.setPeerCount(castToLong(arr[57]));
                    p2PServerCountEntity.setProgramCount(castToLong(arr[58]));
                }
                //p2p server programTop
                if(heat!=null&&!heat.equals("-")){
                    p2PServerProgramTopEntity = new P2PServerProgramTopEntity();
                    p2PServerProgramTopEntity.setDate(date);
                    p2PServerProgramTopEntity.setTime(time);
                     if(arr[55].equals("1")){
                         p2PServerProgramTopEntity.setNetName("联通");
                    }else if(arr[55].equals("2")){
                         p2PServerProgramTopEntity.setNetName("移动");
                    }else if(arr[55].equals("3")){
                         p2PServerProgramTopEntity.setNetName("电信");
                    }
                    Long dt=new Long(arr[56]+"000");
                    p2PServerProgramTopEntity.setDateTime(sdf.format(dt));
                    p2PServerProgramTopEntity.setHeat(castToLong(arr[59]));
                    p2PServerProgramTopEntity.setUrl(arr[60]);
                    p2PServerProgramTopEntity.setTopK(castToLong(arr[61]));
                }
                //p2p server duration
                if(duration!=null&&!duration.equals("-")){
                    p2PServerDurationEntity = new P2PServerDurationEntity();
                    p2PServerDurationEntity.setDate(date);
                    p2PServerDurationEntity.setTime(time);
                    if(arr[55].equals("1")){
                        p2PServerDurationEntity.setNetName("联通");
                    }else if(arr[55].equals("2")){
                        p2PServerDurationEntity.setNetName("移动");
                    }else if(arr[55].equals("3")){
                        p2PServerDurationEntity.setNetName("电信");
                    }
                    Long dt=new Long(arr[56]+"000");
                    p2PServerDurationEntity.setDateTime(sdf.format(dt));
                    p2PServerDurationEntity.setUserCount(castToLong(arr[62]));
                    p2PServerDurationEntity.setDuration(castToLong(arr[63]));
                }

                //p2p nat
                if(nat!=null&&!nat.equals("-")){
                    p2PNatEntity = new P2PNatEntity();
                    if(nat.equals("-1")){
                        p2PNatEntity.setUnknownCount(1);
                    }else if(nat.equals("0")){
                        p2PNatEntity.setNonOneCount(1);
                    }else if(nat.equals("1")){
                        p2PNatEntity.setNonTwoCount(1);
                    }else if(nat.equals("2")){
                        p2PNatEntity.setNonThreeCount(1);
                    }else if(nat.equals("3")){
                        p2PNatEntity.setSymmeCount(1);
                    }else if(nat.equals("4")){
                        p2PNatEntity.setSendData(1);
                    }else if(nat.equals("5")){
                        p2PNatEntity.setReceiveBack(1);
                    }else if(nat.equals("6")){
                        p2PNatEntity.setOutTime(1);
                    }
                    if (area != null) {
                        p2PNatEntity.setNetName(area.getNetName());
                    }
                    p2PNatEntity.setDate(date);
                    p2PNatEntity.setTime(time);
                    p2PNatEntity.setDateTime(date + " " + time);
                }
                collector.emit(new Values(peerLogEntity, pinfo,p2PAcceptEntity,p2PSendEntity,p2PSingleEntity,p2PServerCountEntity,p2PServerProgramTopEntity,p2PServerDurationEntity,p2PNatEntity));
            }
        } catch (Exception er) {
            er.printStackTrace();
        }finally {
            collector.ack(input);
        }
    }

    //    private String getDate(String dateTime){
//        DateUtil.parseDate()
//    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logEntity", "peerInfo","p2pRes", "p2pSend", "p2pSingle","p2pServerCount","p2pServerProgramTop","p2pServerDuration","p2pNat"));
    }

    private long castToLong(String v){
        try{
            return Long.valueOf(v);
        }catch (Exception er) {
            return 0;
        }
    }

    private int castToInt(String v){
        try{
            return Integer.valueOf(v);
        }catch (Exception er) {
            return 0;
        }
    }
}
