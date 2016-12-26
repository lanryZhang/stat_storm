package com.ifeng.persistence.vdn;

import com.ifeng.constant.ReflectWhere;
import com.ifeng.core.query.Where;
import com.ifeng.entities.vdn.VdnEntity;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;
import com.ifeng.persistence.IFlush;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyb on 2016/10/25.
 */
public class VdnPcFlush implements IFlush {
    @Override
    public void flush(Object... objects) {
        try {

            ConcurrentHashMap<String, ConcurrentHashMap<VdnEntity, Integer>> map = (ConcurrentHashMap<String, ConcurrentHashMap<VdnEntity, Integer>>) objects[0];
            MongoCli cli = MongoFactory.createMongoClient();
            cli.changeDb("vdnstat_test");
            for (Map.Entry<String, ConcurrentHashMap<VdnEntity, Integer>> item : map.entrySet()) {
                cli.getCollection(item.getKey());
                for (Map.Entry<VdnEntity, Integer> entry : item.getValue().entrySet()) {
                    VdnEntity en = entry.getKey();
                    Where where = ReflectWhere.toWhere(en);
                    Map<String, Number> fields = new HashMap<String, Number>();
                    fields.put("firstFrame", entry.getValue());
                    fields.put("requestSum", entry.getValue());
                    fields.put("pausedOne", entry.getValue());
                    fields.put("pausedTwo", entry.getValue());
                    fields.put("pausedThree", entry.getValue());
                    fields.put("pausedMore", entry.getValue());
                    fields.put("flowFault", entry.getValue());
                    fields.put("seekFault", entry.getValue());
                    fields.put("imperfectFault", entry.getValue());
                    fields.put("otherflowF", entry.getValue());
                    fields.put("ipPortTime", entry.getValue());
                    fields.put("ipsPortFai", entry.getValue());
                    fields.put("AllSum", entry.getValue());
                    cli.inc(en, fields, where, true);
                    Thread.sleep(5);
                }
            }
            map.clear();
        } catch (Exception err) {
        }
    }

}
