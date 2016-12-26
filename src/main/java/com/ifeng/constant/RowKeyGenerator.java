package com.ifeng.constant;

import java.security.MessageDigest;

/**
 * Created by zhanglr on 2016/10/13.
 */
public class RowKeyGenerator {
    public static String generateRowKey(String srcStr) throws Exception {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] cipherData = md5.digest(srcStr.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte cipher : cipherData) {
                String toHexStr = Integer.toHexString(cipher & 0xff);
                builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
            }

            return builder.toString().substring(8,16);
        } catch (Exception e) {
            throw e;
        }
    }
}
