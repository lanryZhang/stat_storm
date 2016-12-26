/*
* IMap.java 
* Created on  202016/12/24 13:10 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.persistence;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public interface IMap<K,V> {

    void put(K key, V value);
}
