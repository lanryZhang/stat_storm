package com.ifeng.persistence;

import com.ifeng.constant.ReflectWhere;
import com.ifeng.entities.*;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;
import com.ifeng.core.query.Where;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class P2PLogFlush implements IFlush{
    private String dbName;
    /**
     *
     * @param dbName 数据库名称
     */
    public P2PLogFlush(String dbName){
        this.dbName = dbName;
    }
    @Override
    public void flush(Object... objects) {
        MongoCli cli = MongoFactory.createMongoClient();
        int count=0;
        try {
            cli.changeDb(dbName);
            ConcurrentHashMap<String, ConcurrentHashMap<Object, Object>> map = (ConcurrentHashMap<String, ConcurrentHashMap<Object, Object>>) objects[0];
            for (Map.Entry<String, ConcurrentHashMap<Object, Object>> e : map.entrySet()) {
                cli.getCollection(e.getKey());
                if(e.getKey().equals("p2p_res")){
                    List<P2PAcceptEntity> list = new ArrayList();
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        list.add((P2PAcceptEntity)item.getValue());
                        count++;
                        if(count%2000==0){
                            cli.insert(list,null);
                            list.clear();
                        }
                    }
                    cli.insert(list,null);
                    count=0;
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_send")){
                    List<P2PSendEntity> list = new ArrayList();
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        list.add((P2PSendEntity)item.getValue());
                        count++;
                        if(count%2000==0){
                            cli.insert(list,null);
                            list.clear();
                        }
                    }
                    cli.insert(list,null);
                    count=0;
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_single")){
                    List<P2PSingleEntity> list = new ArrayList();
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        list.add((P2PSingleEntity)item.getValue());
                        count++;
                        if(count%2000==0){
                            cli.insert(list,null);
                            list.clear();
                        }
                    }
                    cli.insert(list,null);
                    count=0;
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_single_dateTime")){
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        P2PSingleEntity en = (P2PSingleEntity)item.getValue();
                        Where where = ReflectWhere.toWhere(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("dKdRecvFirstDataSum", ((P2PSingleEntity)item.getValue()).getdKdRecvFirstDataSum());
                        fields.put("dRecvFirstDataSum", ((P2PSingleEntity)item.getValue()).getdRecvFirstDataSum());
                        fields.put("dKnockDoorSum", ((P2PSingleEntity)item.getValue()).getdKnockDoorSum());
                        fields.put("dAskResWithoutP2p", ((P2PSingleEntity)item.getValue()).getdAskResWithoutP2p());
                        cli.inc(en,fields, where, true, null);
                    }
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_server_count")){
                    List<P2PServerCountEntity> list = new ArrayList();
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        list.add((P2PServerCountEntity)item.getValue());
                        count++;
                        if(count%2000==0){
                            cli.insert(list,null);
                            list.clear();
                        }
                    }
                    cli.insert(list,null);
                    count=0;
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_server_program_top")){
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        P2PServerProgramTopEntity en = (P2PServerProgramTopEntity)item.getValue();
                        Where where = ReflectWhere.toWhere(en);
                        cli.update(en, where, true,new Date());
                    }
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_server_duration")){
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        P2PServerDurationEntity en = (P2PServerDurationEntity)item.getValue();
                        Where where = ReflectWhere.toWhere(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("userCount", ((P2PServerDurationEntity)item.getValue()).getUserCount());
                        cli.inc(en, fields, where, true,new Date());
                    }
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_nat")){
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        P2PNatEntity en = (P2PNatEntity)item.getValue();
                        Where where = ReflectWhere.toWhereNat(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("unknownCount", ((P2PNatEntity)item.getValue()).getUnknownCount());
                        fields.put("nonOneCount", ((P2PNatEntity)item.getValue()).getNonOneCount());
                        fields.put("nonTwoCount", ((P2PNatEntity)item.getValue()).getNonTwoCount());
                        fields.put("nonThreeCount", ((P2PNatEntity)item.getValue()).getNonThreeCount());
                        fields.put("symmeCount", ((P2PNatEntity)item.getValue()).getSymmeCount());
                        fields.put("sendData", ((P2PNatEntity)item.getValue()).getSendData());
                        fields.put("receiveBack", ((P2PNatEntity)item.getValue()).getReceiveBack());
                        fields.put("outTime", ((P2PNatEntity)item.getValue()).getOutTime());
                        cli.inc(en, fields, where, true,new Date());
                    }
                    Thread.sleep(1);
                }else if(e.getKey().equals("p2p_nat_dateTime")){
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        P2PNatEntity en = (P2PNatEntity)item.getValue();
                        Where where = ReflectWhere.toWhere(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("unknownCount", ((P2PNatEntity)item.getValue()).getUnknownCount());
                        fields.put("nonOneCount", ((P2PNatEntity)item.getValue()).getNonOneCount());
                        fields.put("nonTwoCount", ((P2PNatEntity)item.getValue()).getNonTwoCount());
                        fields.put("nonThreeCount", ((P2PNatEntity)item.getValue()).getNonThreeCount());
                        fields.put("symmeCount", ((P2PNatEntity)item.getValue()).getSymmeCount());
                        fields.put("sendData", ((P2PNatEntity)item.getValue()).getSendData());
                        fields.put("receiveBack", ((P2PNatEntity)item.getValue()).getReceiveBack());
                        fields.put("outTime", ((P2PNatEntity)item.getValue()).getOutTime());
                        cli.inc(en, fields, where, true,new Date());
                    }
                    Thread.sleep(1);
                }else{
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        PeerLogEntity en = (PeerLogEntity)item.getKey();
                        Where where = ReflectWhere.toWhere(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("p2p_DlBytes", ((PeerInfo)item.getValue()).getP2p_DlBytes());
                        fields.put("cdn_DlBytes", ((PeerInfo)item.getValue()).getCdn_DlBytes());
                        fields.put("cdnDoneCount", ((PeerInfo)item.getValue()).getCdnDoneCount());
                        fields.put("p2pDoneCount", ((PeerInfo)item.getValue()).getP2pDoneCount());
                        fields.put("p2pSendCount", ((PeerInfo)item.getValue()).getP2pSendCount());
                        fields.put("p2pSendCountPre", ((PeerInfo)item.getValue()).getP2pSendCountPre());
                        cli.inc(en, fields, where, true,new Date());
                    }
                    Thread.sleep(1);
                }
            }
            map.clear();
        }catch (Exception err){
            err.printStackTrace();
        }
        finally {
            if (cli != null){
                try {
                    cli.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
