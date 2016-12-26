package com.ifeng.hbase;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhanglr on 2016/10/12.
 */
public interface IHBaseClient {
    void changeTable(String namespace,String tableName) throws IOException;

    <T extends HBaseCodec> void insert(T t) throws IOException;

    <T extends HBaseCodec> void insertMany(List<T> list) throws IOException;

    <T extends HBaseCodec> List<T> getList(HBaseSelect select,Class<T> clazz) throws Exception;

    <T extends HBaseCodec> T getOne(HBaseSelect select, Class<T> clazz) throws Exception;

    void createTable(String tableName, String namespaces, String[] families) throws IOException;

    void dropTable(String namespace, String tableName) throws Exception;

    void dropTable(String tableName) throws Exception;

    boolean existsTable(String tableName) throws Exception;

    void createNamespace(String namespace) throws Exception;

    void deleteNamespace(String tableName) throws Exception;

    <T extends HBaseCodec> Boolean incr(T t) throws Exception;
    Long incr(String row,String family,String qualifier,Long value) throws Exception;

    <T extends HBaseCodec> Boolean incr(List<T> list) throws Exception;
    void close();
}
