package com.ifeng.persistence;

import com.ifeng.entities.P2PSendEntity;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gutc on 2016/8/19.
 */
public class P2PSendFlush  implements IFlush {
    private String dbName;
    /**
     *
     * @param dbName 数据库名称
     */
    public P2PSendFlush(String dbName){
        this.dbName = dbName;
    }
    @Override
    public void flush(Object... objects) {
        MongoCli cli = MongoFactory.createMongoClient();
        List<P2PSendEntity> p2PSendEntityList = new ArrayList();
        int count = 0;
        try {
            cli.changeDb(dbName);
            ConcurrentHashMap<String, ConcurrentHashMap<String, P2PSendEntity>> map = (ConcurrentHashMap<String, ConcurrentHashMap<String, P2PSendEntity>>) objects[0];
            for (Map.Entry<String, ConcurrentHashMap<String, P2PSendEntity>> e : map.entrySet()) {
                cli.getCollection(e.getKey());
                for (Map.Entry<String, P2PSendEntity> item : e.getValue().entrySet()) {
                    p2PSendEntityList.add(item.getValue());
                    count++;
                    if(count%2000==0){
                        cli.insert(p2PSendEntityList,null);
                        p2PSendEntityList.clear();
                    }
                }
                cli.insert(p2PSendEntityList,null);
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
