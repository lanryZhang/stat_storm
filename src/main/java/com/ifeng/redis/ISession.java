package com.ifeng.redis;

import javax.servlet.http.HttpServletRequest;

public interface ISession {
	/**
	 * 获取缓存值 自动延期
	 * @param request
	 * @param key
	 * @return
	 */
	public <T> T session(HttpServletRequest request, String key)  throws Exception ;
	
	/**
	 * 保存缓存值 设置有效期
	 * @param request
	 * @param key
	 * @param value
	 */
	public <T> void session(HttpServletRequest request, String key, T value)  throws Exception ;
	
	/**
	 * 清空session
	 * @param request
	 */
	public void clearSession(HttpServletRequest request)  throws Exception ;
	
	/**
	 * 回收Jedis对象到连接池
	 */
	public void returnResource();

	/**
	 * 回收Jedis对象到连接池
	 */
	public void returnBrokenResource();
}
