package com.ifeng.redis;

import com.ifeng.core.SerializeUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.*;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisClient implements IRedis{
    private String redisName;
    private JedisCommands jedis;
    private static JedisPool jedisPool;
    private static ShardedJedisPool shardedJedisPool;
    private boolean useSharded = false;
    private String shardedName;
    private static final Logger logger = Logger.getLogger(RedisClient.class);

    public RedisClient(String name) {
        this.redisName = name;
        initJedis();
    }

    public RedisClient(String name,Boolean sharded) {
        this.useSharded = sharded;
        if (useSharded){
            this.shardedName = name;
            initShardedJedis();
        }else{
            this.redisName = name;
            initJedis();
        }
    }

    public void initJedis() {
        if (null == jedisPool) {
            synchronized (RedisClient.class) {
                if (null == jedisPool) {
                    jedisPool = new Redis().getJedisPool(redisName);
                }
            }
        }
    }

    public void initShardedJedis(){
        if (null == shardedJedisPool) {
            synchronized (RedisClient.class) {
                if (null == shardedJedisPool) {
                    shardedJedisPool = new Redis().getShardedJedisPool(shardedName);
                }
            }
        }
    }

    private void getResource() throws Exception {
        try {
            if (useSharded){
                jedis = shardedJedisPool.getResource();
                if (!((Jedis)jedis).isConnected()) {
                    ((Jedis)jedis).connect();
                }
            }else {
                jedis = jedisPool.getResource();
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }


    @Override
    public <T> List<T> getList(String key, Class<T> clazz) throws Exception {
        try {
            getResource();
            List<String> list = jedis.lrange(key, 0, -1);
            List<T> res = new ArrayList<>();
            T t;
            for (String bs : list) {
                t = SerializeUtil.toObject(bs, clazz);
                res.add(t);
            }
            return res;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public long incr(String key) throws Exception {
        try {
            getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) throws Exception {
        try {
            getResource();
            String en = jedis.get(key);
            // 反序列化
            return SerializeUtil.toObject(en, clazz);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public Map<String,String> bacthGet(List<String> keys) throws Exception {
        getResource();
        ShardedJedisPipeline shardedJedisPipeline = null;
        Pipeline pipeline = null;
        if (useSharded){
            shardedJedisPipeline = ((ShardedJedis)jedis).pipelined();
        }else{
            pipeline = ((Jedis)jedis).pipelined();
        }
        try {
            Map<String,Response<String>> pipmap = new ConcurrentHashMap<>();
            Map<String,String> resMap = new ConcurrentHashMap<>();
            for (String key : keys){
                pipmap.put(key,(null == pipeline?shardedJedisPipeline:pipeline).get(key));
            }
            pipeline.sync();

            for (Map.Entry<String,Response<String>> en:pipmap.entrySet()){
                resMap.put(en.getKey(),en.getValue().get());
            }

            return resMap;

        }catch (Exception e){
            logger.error(e);
            throw e;
        }finally {
            close();
            if (null != pipeline){
                pipeline.clear();
                pipeline.close();
            }
        }
    }

    @Override
    public <T> Map<String,T> bacthGet(List<String> keys, Class<T> clazz) throws Exception {
        getResource();
        ShardedJedisPipeline shardedJedisPipeline = null;
        Pipeline pipeline = null;
        if (useSharded){
            shardedJedisPipeline = ((ShardedJedis)jedis).pipelined();
        }else{
            pipeline = ((Jedis)jedis).pipelined();
        }

        try {
            Map<String,Response<String>> pipmap = new ConcurrentHashMap<>();
            Map<String,T> resMap = new ConcurrentHashMap<>();
            for (String key : keys){
                pipmap.put(key,(null == pipeline?shardedJedisPipeline:pipeline).get(key));
            }
            pipeline.sync();

            for (Map.Entry<String,Response<String>> en:pipmap.entrySet()){
                resMap.put(en.getKey(),SerializeUtil.toObject(en.getValue().get(),clazz));
            }
            return resMap;

        }catch (Exception e){
            logger.error(e);
            throw e;
        }finally {
            if (null != pipeline){
                pipeline.clear();
                pipeline.close();
            }
            close();
        }
    }

    @Override
    public String getString(String key) throws Exception {
        try {
            getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public List<String> getStringList(String key) throws Exception {
        try {
            getResource();
            return jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }

    }

    @Override
    public void setListString(String key, List<String> list) throws Exception {
        try {
            getResource();
            jedis.del(key);
            for (String v : list) {
                jedis.lpush(key, v);
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }

    }

    @Override
    public <T> String set(String key, T t) throws Exception {
        try {
            getResource();
            return jedis.set(key, SerializeUtil.toJsonString(t));
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public <T> void set(String key, List<T> list) throws Exception {
        try {
            getResource();
            jedis.del(key);
            for (T t : list) {
                String values = SerializeUtil.toJsonString(t);
                jedis.lpush(key, values);
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public String setString(String key, String value) throws Exception {
        try {
            getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }


    @Override
    public <T> void set(String key, List<T> list, int expire) throws Exception {
        try {
            getResource();
            jedis.del(key);
            for (T t : list) {
                String values = SerializeUtil.toJsonString(t);
                jedis.lpush(key, values);
            }
            jedis.expire(key, expire);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public <T> String set(String key, T t, int expire) throws Exception {
        try {
            getResource();
            String values = SerializeUtil.toJsonString(t);
            String res = jedis.set(key, values);
            jedis.expire(key, expire);
            return res;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public String setString(String key, String value, int expire) throws Exception {
        try {
            getResource();
            String res = jedis.set(key, value);
            jedis.expire(key, expire);
            return res;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void setListString(String key, List<String> list, int expire) throws Exception {
        try {
            getResource();
            jedis.del(key);
            for (String str : list) {
                jedis.lpush(key, str);
            }
            jedis.expire(key, expire);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void expireKey(String key, int expire) throws Exception {
        try {
            getResource();
            jedis.expire(key, expire);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (jedis != null) {
                ((Closeable)jedis).close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <T> void lpush(String key, T t) throws Exception {
        try {
            getResource();
            jedis.lpush(key, SerializeUtil.toJsonString(t));
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void lpushString(String key, String value) throws Exception {
        try {
            getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public List<String> hmget(String key, String... keys) throws Exception {
        try {
            getResource();
            return jedis.hmget(key, keys);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public String hmset(String key, Map<String,String> map) throws Exception {
        try {
            getResource();
            return jedis.hmset(key, map);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public <T> T lpop(String key, Class<T> clazz) throws Exception {
        try {
            getResource();
            String res = jedis.lpop(key);
            return SerializeUtil.toObject(res, clazz);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public String lpopString(String key) throws Exception {
        try {
            getResource();
            String res = jedis.lpop(key);
            return res;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }

    }

    @Override
    public long del(String key) throws Exception {
        try {
            getResource();
            return jedis.del(key);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }

    }

    @Override
    public boolean existsString(String key) throws Exception {
        try {
            getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            close();
        }

    }
}
