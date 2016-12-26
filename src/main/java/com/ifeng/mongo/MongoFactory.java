package com.ifeng.mongo;


import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

public class MongoFactory {

	private static MongoCli instance;

	/**
	 * 默认数据库
	 * @return
	 */
	public static MongoCli getInstance()  {
		if (instance == null){
			synchronized (MongoFactory.class) {
				if (instance == null){
					ServerAddress addr21 = new ServerAddress("10.50.16.21", 27021);
					ServerAddress addr35 = new ServerAddress("10.50.16.35", 27021);
					ServerAddress addr36 = new ServerAddress("10.50.16.36", 27021);
					ServerAddress addr18 = new ServerAddress("10.50.16.18", 27021);
					ServerAddress addr20 = new ServerAddress("10.50.16.20", 27017);
					List<ServerAddress> list = new ArrayList<ServerAddress>();
					list.add(addr18);
					list.add(addr21);
					list.add(addr35);
					list.add(addr36);
					instance = new MongoCli(list,new ArrayList<>());
				}
			}
		}
		return instance;
	}

	public static MongoCli createMongoClient(){
		ServerAddress addr21 = new ServerAddress("10.50.16.21", 27021);
		ServerAddress addr35 = new ServerAddress("10.50.16.35", 27021);
		ServerAddress addr36 = new ServerAddress("10.50.16.36", 27021);
		ServerAddress addr18 = new ServerAddress("10.50.16.18", 27021);
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		list.add(addr18);
		list.add(addr21);
		list.add(addr35);
		list.add(addr36);
		return new MongoCli(list,new ArrayList<>());
	}


	public static MongoCli createPgcMongoClient(){
		ServerAddress addr14 = new ServerAddress("wemedia_db_14_syq", 27000);
		ServerAddress addr15 = new ServerAddress("wemedia_db_15_syq", 27000);
		ServerAddress addr16 = new ServerAddress("wemedia_db_16_syq", 27000);
		ServerAddress addr17 = new ServerAddress("wemedia_db_17_syq", 27000);
		ServerAddress addr18 = new ServerAddress("wemedia_db_18_syq", 27000);
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		list.add(addr14);
		list.add(addr15);
		list.add(addr16);
		list.add(addr17);
		list.add(addr18);
		return new MongoCli(list,new ArrayList<>());
	}
	/**
	 * 初始化Mongo实例，并且切换到指定数据库
	 * @param dbname
	 * @return
	 */
//	public static MongoCli getInstance(String dbname){
//		if (instance == null){
//			synchronized (MongoFactory.class) {
//				if (instance == null){
//					instance = new MongoCli();
//					instance.changeDb(dbname);
//				}
//			}
//		}
//		return instance;
//	}
}
