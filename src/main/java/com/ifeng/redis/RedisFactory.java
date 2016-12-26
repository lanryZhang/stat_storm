package com.ifeng.redis;


public class RedisFactory {
	private static RedisClient redisClient;
	

	public static RedisClient getSingletonRedisClient() {
		if (redisClient == null){
			synchronized (RedisFactory.class) {
				if (redisClient == null){
					redisClient = new RedisClient("redis_1");	
				}
			}
		}
		return redisClient;
	}


	public static RedisClient newInstance(){
		return new RedisClient("redis_1");
	}

	public static RedisClient newInstance(String name){
		return new RedisClient(name);
	}

	public static RedisClient newInstance(String name,boolean sharded){
		return new RedisClient(name,sharded);
	}

}
