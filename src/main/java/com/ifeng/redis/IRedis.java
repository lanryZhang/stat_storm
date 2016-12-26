package com.ifeng.redis;

import java.util.List;
import java.util.Map;

public interface IRedis {
    /**
     * 获取list对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    <T> List<T> getList(String key, Class<T> clazz) throws Exception;

    long incr(String key) throws Exception;

    /**
     * 获取存储对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    <T> T get(String key, Class<T> clazz) throws Exception;

    Map<String,String> bacthGet(List<String> keys) throws Exception;

    <T> Map<String,T> bacthGet(List<String> keys, Class<T> clazz) throws Exception;
    /**
     * 获取存储的值 --字符串类型
     *
     * @param key
     * @return
     * @throws Exception
     */
    String getString(String key) throws Exception;

    /**
     * 获取List<String>
     *
     * @param key
     * @return
     * @throws Exception
     */
    List<String> getStringList(String key) throws Exception;

    /**
     * 存储list对象
     *
     * @param key
     * @param list
     * @throws Exception
     */
    <T> void set(String key, List<T> list) throws Exception;

    /**
     * 存储list对象，并且设置过期时间
     *
     * @param key
     * @param list
     * @param expire 秒
     * @throws Exception
     */
    <T> void set(String key, List<T> list, int expire) throws Exception;

    /**
     * 存储单个实例对象
     *
     * @param key
     * @param t
     * @return
     * @throws Exception
     */
    <T> String set(String key, T t) throws Exception;

    /**
     * 存储单个实例对象，并且设置过期时间
     *
     * @param key
     * @param t
     * @param expire 秒
     * @return
     * @throws Exception
     */
    <T> String set(String key, T t, int expire) throws Exception;

    /**
     * 存储string类型的值
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    String setString(String key, String value) throws Exception;

    /**
     * 存储string类型的值，并且设置过期时间
     *
     * @param key
     * @param value
     * @param expire 秒
     * @return
     * @throws Exception
     */
    String setString(String key, String value, int expire) throws Exception;

    /**
     * 存储List<String>对象
     *
     * @param key
     * @param list
     * @throws Exception
     */
    void setListString(String key, List<String> list) throws Exception;

    /**
     * 存储List<String>对象,并且设置过期时间
     *
     * @param key
     * @param list
     * @param expire 秒
     * @throws Exception
     */
    void setListString(String key, List<String> list, int expire) throws Exception;

    /**
     * List中插入一条数据
     *
     * @param key
     * @param t
     * @throws Exception
     */
    <T> void lpush(String key, T t) throws Exception;

    String hmset(String key, Map<String, String> map) throws Exception;

    /**
     * 弹出一条数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    <T> T lpop(String key, Class<T> clazz) throws Exception;

    /**
     * List中插入一条string类型数据
     *
     * @param key
     * @param value
     * @throws Exception
     */
    void lpushString(String key, String value) throws Exception;

    /**
     * List中弹出一条string类型数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    String lpopString(String key) throws Exception;

    /**
     * 删除一个String类型的键值对
     *
     * @param key
     * @return
     * @throws Exception
     */
    long del(String key) throws Exception;

    /**
     * 判断是否存在一个String类型的key
     *
     * @param key
     * @return
     * @throws Exception
     */
    boolean existsString(String key) throws Exception;

    /**
     * 设置缓存过期时间
     *
     * @param key
     * @param expire
     * @throws Exception
     */
    void expireKey(String key, int expire) throws Exception;


    void close() throws Exception;

    List<String> hmget(String key, String... keys) throws  Exception;
}
