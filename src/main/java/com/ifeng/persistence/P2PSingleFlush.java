package com.ifeng.persistence;

import com.ifeng.entities.P2PSingleEntity;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gutc on 2016/8/19.
 */
public class P2PSingleFlush  implements IFlush {
    private String dbName;
    /**
     *
     * @param dbName 数据库名称
     */
    public P2PSingleFlush(String dbName){
        this.dbName = dbName;
    }
    @Override
    public void flush(Object... objects) {
        MongoCli cli = MongoFactory.createMongoClient();
        List<P2PSingleEntity> p2PSingleEntityList = new ArrayList();
        int count = 0;
        try {
            cli.changeDb(dbName);
            ConcurrentHashMap<String, ConcurrentHashMap<String, P2PSingleEntity>> map = (ConcurrentHashMap<String, ConcurrentHashMap<String, P2PSingleEntity>>) objects[0];
            for (Map.Entry<String, ConcurrentHashMap<String, P2PSingleEntity>> e : map.entrySet()) {
                cli.getCollection(e.getKey());
                for (Map.Entry<String, P2PSingleEntity> item : e.getValue().entrySet()) {
                    p2PSingleEntityList.add(item.getValue());
                    count++;
                    if(count%2000==0){
                        cli.insert(p2PSingleEntityList,null);
                        p2PSingleEntityList.clear();
                    }
                }
                cli.insert(p2PSingleEntityList,null);
                Thread.sleep(1);
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
