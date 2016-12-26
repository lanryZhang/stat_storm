/*
* MD5Util.java 
* Created on  202016/12/19 11:34 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.utils;

import java.security.MessageDigest;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class MD5Util {
    public static String md5(String value){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
            StringBuffer buf=new StringBuffer();
            for(byte b:md.digest()){
                buf.append(String.format("%02x", b&0xff));
            }
            return  buf.toString();
        }catch( Exception e ){
            e.printStackTrace();

            return null;
        }
    }
}
