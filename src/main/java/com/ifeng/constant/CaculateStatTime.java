/*
* CaculateStatTime.java 
* Created on  202016/12/2 14:14 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.constant;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class CaculateStatTime {


    /**
     *
     * @param oldTimeValue
     * @param scrop 时间范围
     * @param len 生成结果最低长度
     * @return
     */
    public static String caculte(int oldTimeValue,int scrop,int len){
        int mod = oldTimeValue % scrop;
        int res = oldTimeValue - mod;
        return String.format("%0"+len+"d",res);
}

    public static String caculte(String oldTimeValue,int scrop,int len){
        return caculte(Integer.valueOf(oldTimeValue),scrop,len);
    }
}
