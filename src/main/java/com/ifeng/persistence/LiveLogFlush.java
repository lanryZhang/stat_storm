package com.ifeng.persistence;

import com.ifeng.entities.LiveLogEntities;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhusy on 2016/9/30.
 */
public class LiveLogFlush implements IFlush{
    private String dbName;
    /**
     *
     * @param dbName 数据库名称
     */
    public LiveLogFlush(String dbName){
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
                if(e.getKey().equals("live_stat")){
                    List<LiveLogEntities> list = new ArrayList();
                    for (Map.Entry<Object, Object> item : e.getValue().entrySet()) {
                        list.add((LiveLogEntities)item.getValue());
                        count++;
                        if(count%2000==0){
                            cli.insert(list,null);
                            list.clear();
                        }
                    }
                    cli.insert(list,null);
                    count=0;
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

