package com.ifeng.persistence;

import com.ifeng.constant.ReflectWhere;
import com.ifeng.entities.IpsEntity;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;
import com.ifeng.core.query.Where;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhanglr on 2016/4/12.
 */
public class IpsLogFlush implements  IFlush {
    @Override
    public void flush(Object... objects) {
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<IpsEntity, Integer>> map = (ConcurrentHashMap<String, ConcurrentHashMap<IpsEntity, Integer>>) objects[0];
            MongoCli cli = MongoFactory.createMongoClient();
            cli.changeDb("ipstest");
            for (Map.Entry<String, ConcurrentHashMap<IpsEntity, Integer>> item : map.entrySet()) {
                //if (item.getKey().equals("hostIp"))
                cli.getCollection(item.getKey());
                for (Map.Entry<IpsEntity, Integer> entry : item.getValue().entrySet()) {
                    IpsEntity en = entry.getKey();
                    Where where = ReflectWhere.toWhere(en);
                    Map<String, Number> fields = new HashMap<String, Number>();
                    fields.put("requestNum", entry.getValue());
                    cli.inc(en, fields, where, true,new Date());
                    Thread.sleep(5);
                }
            }
            map.clear();
        }
        catch (Exception err){
        }
    }
}
