package com.ifeng.persistence;

import com.ifeng.constant.ReflectWhere;
import com.ifeng.entities.BandWidthEntity;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;
import com.ifeng.core.query.Where;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class NginxLogFlush implements IFlush {
    private String dbName;
    /**
     *
     * @param dbName 数据库名称
     */
    public NginxLogFlush(String dbName){
        this.dbName = dbName;
    }
    @Override
    public void flush(Object... objects) {
        MongoCli cli = MongoFactory.createMongoClient();
        try {
            cli.changeDb(dbName);
            ConcurrentHashMap<String, ConcurrentHashMap<String, BandWidthEntity>> map = (ConcurrentHashMap<String, ConcurrentHashMap<String, BandWidthEntity>>) objects[0];
            for (Map.Entry<String, ConcurrentHashMap<String, BandWidthEntity>> e : map.entrySet()) {
                cli.getCollection(e.getKey());
                if(e.getKey().equals("bandwidth")){
                    for (Map.Entry<String, BandWidthEntity> item : e.getValue().entrySet()) {
                        BandWidthEntity en = item.getValue();
                        Where where = ReflectWhere.toWhere(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("bodyBytesSent", en.getBodyBytesSent());
                        cli.inc(en, fields, where, true);
                        Thread.sleep(1);
                    }
                }else if(e.getKey().equals("bandwidth_netName")){
                    for (Map.Entry<String, BandWidthEntity> item : e.getValue().entrySet()) {
                        BandWidthEntity en = item.getValue();
                        Where where = ReflectWhere.toWhereNetName(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("bodyBytesSent", en.getBodyBytesSent());
                        cli.inc(en, fields, where, true);
                        Thread.sleep(1);
                    }
                }else if(e.getKey().equals("bandwidth_netName_type")){
                    for (Map.Entry<String, BandWidthEntity> item : e.getValue().entrySet()) {
                        BandWidthEntity en = item.getValue();
                        Where where = ReflectWhere.toWhereNetNameAndType(en);
                        Map<String, Number> fields = new HashMap<String, Number>();
                        fields.put("bodyBytesSent", en.getBodyBytesSent());
                        cli.inc(en, fields, where, true);
                        Thread.sleep(1);
                    }
                }

            }
            map.clear();
        }catch (Exception err){}
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
