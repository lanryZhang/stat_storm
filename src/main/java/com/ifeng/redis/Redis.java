package com.ifeng.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ifeng.configuration.RedisConfig;

import com.ifeng.configuration.RedisConfigManage;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class Redis {
	private Map<String, RedisConfig> redisConfigs;
	private Map<String, JedisPoolConfig> redisPoolConfigs;
	private Map<String, JedisPool> redisPools;
	private Map<String, ShardedJedisPool> sharedRedisPools;
	private Map<String, List<RedisConfig>> sharedRedisPoolConfigs;
	private JedisPool jedisPool;
	private ShardedJedisPool shardedJedisPool;
	private List<JedisShardInfo> shardInfos;
	private final Logger logger = Logger.getLogger(Redis.class);
	/**
	 * 根据实例名获取Redis单实例
	 * 
	 * @param instanceName
	 *            实例名称--redis.xml中配置的name属性
	 * @return
	 */
	public JedisPool getJedisPool(String instanceName) {
		try {
			initConfigs();
			if (jedisPool == null) {
				if (redisPools.get(instanceName) == null) {
					logger.error("无法获取Redis实例，请检查配置文件是否正确.");
					throw new Exception("无法获取Redis实例，请检查配置文件是否正确.");
				}
				jedisPool = redisPools.get(instanceName);
			}

			if (jedisPool == null) {
				logger.error("无法获取Redis实例，请检查配置文件是否正确.");
				throw new Exception("无法获取Redis实例，请检查配置文件是否正确.");
			}
			return jedisPool;
		} catch (Exception e) {
			jedisPool = null;
		}
		return null;
	}

	/**
	 * 根据名称获取sharding实例
	 * 
	 * @param shardName
	 *            shard名称，--redis.xml中配置的shard属性
	 * @return
	 */
	public ShardedJedisPool getShardedJedisPool(String shardName) {
		try {
			initConfigs();
			if (shardedJedisPool == null) {
				shardedJedisPool = sharedRedisPools.get(shardName);
				if (shardedJedisPool == null) {
					logger.error("无法实例化Redis，请检查配置文件是否正确.");
					throw new Exception("无法实例化Redis，请检查配置文件是否正确.");
				} else {
					// shardClient = shardedJedisPool.getResource();
				}
			}
			return shardedJedisPool;
		} catch (Exception e) {
			shardedJedisPool = null;
		}
		return null;
	}

	public List<JedisShardInfo> getShardInfos() {
		try {
			if (shardInfos == null || shardInfos.size() == 0) {
				initConfigs();
			}
		} catch (Exception e) {
			shardInfos = null;
		}
		return shardInfos;
	}

	/**
	 * 初始化所有配置文件信息
	 * 
	 * @throws Exception
	 */
	private void initConfigs() throws Exception {
		initRedisConfigs();
		initRedisPoolConfigs();
		initRedisPools();
		initShardRedisPools();
	}

	/**
	 * 初始化Redis配置信息
	 */
	private void initRedisConfigs() throws Exception {
		if (redisConfigs == null) {
			redisConfigs = new RedisConfigManage("redis.properties").getRedisConfigs();
		}
	}

	/**
	 * 初始化Redis连接池配置
	 */
	private void initRedisPoolConfigs() throws Exception {
		if (redisPoolConfigs == null) {
			redisPoolConfigs = new HashMap<>();
		} else {
			return;
		}

		if (sharedRedisPoolConfigs == null) {
			sharedRedisPoolConfigs = new HashMap<>();
		}

		JedisPoolConfig en = null;
		for (Entry<String, RedisConfig> config : redisConfigs.entrySet()) {
			String shard = config.getValue().getShard();// 获取分片标记

			// 实例化RedisPoolConfig
			en = new JedisPoolConfig();
			en.setMaxTotal(config.getValue().getMaxTotal());
			en.setMaxIdle(config.getValue().getMaxIdle());
			en.setMaxWaitMillis(config.getValue().getMaxWait());
			en.setTestOnBorrow(config.getValue().isTestOnBorrow());
			en.setTestOnReturn(config.getValue().isTestOnReturn());
			en.setMinEvictableIdleTimeMillis(config.getValue().getMinEvictableIdleTimeMillis());
			en.setNumTestsPerEvictionRun(config.getValue().getNumTestsPerEvictionRun());
			en.setTestWhileIdle(config.getValue().isTestWhileIdle());
			en.setTimeBetweenEvictionRunsMillis(config.getValue().getTimeBetweenEvictionRunsMillis());
			redisPoolConfigs.put(config.getKey(), en);

			// 如果分片标记不为空，则表示该节点属于集群中的一个节点
			if (shard != null && !shard.equals("")) {
				List<RedisConfig> list = sharedRedisPoolConfigs.get(shard);
				if (list == null) {
					list = new ArrayList<>();
					sharedRedisPoolConfigs.put(shard, list);
				}
				list.add(config.getValue());
			}
		}
	}

	/**
	 * 初始化非分片Redis连接池
	 */
	private void initRedisPools() throws Exception {
		if (redisPools == null) {
			redisPools = new HashMap<>();
		} else {
			return;
		}
		JedisPool pool;
		for (Entry<String, JedisPoolConfig> config : redisPoolConfigs.entrySet()) {
			RedisConfig c1 = redisConfigs.get(config.getKey());
			pool = new JedisPool(config.getValue(), c1.getServerIp(), c1.getPort());

			redisPools.put(config.getKey(), pool);
		}
	}

	/**
	 * 初始化分片Redis连接池
	 */
	private void initShardRedisPools() throws Exception {
		if (sharedRedisPools == null) {
			sharedRedisPools = new HashMap<>();
		} else {
			return;
		}
		ShardedJedisPool pool = null;

		JedisPoolConfig rc = null;
		JedisShardInfo en = null;
		for (Entry<String, List<RedisConfig>> config : sharedRedisPoolConfigs.entrySet()) {
			List<RedisConfig> list = config.getValue();
			rc = redisPoolConfigs.get(config.getValue().get(0).getName());
			shardInfos = new ArrayList<>();

			for (RedisConfig redisConfig : list) {
				en = new JedisShardInfo(redisConfig.getServerIp(), redisConfig.getPort(), redisConfig.getName());

				shardInfos.add(en);
			}
			pool = new ShardedJedisPool(rc, shardInfos);
			sharedRedisPools.put(config.getKey(), pool);
		}
	}

}
