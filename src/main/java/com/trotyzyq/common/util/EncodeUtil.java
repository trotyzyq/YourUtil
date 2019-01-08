package com.trotyzyq.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author trotyzyq
 * @Time 2018/8/30.
 */
public class EncodeUtil {
    public static String  sha1Sign(String algorithm,Map<String,String> params){
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
//        MessageDigest messageDigest = null;
//        try {
//            messageDigest = MessageDigest.getInstance(algorithm);
//            messageDigest.update(sb.toString().getBytes());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        byte[] bytes=messageDigest.digest();
//        int len = bytes.length;
//        StringBuilder buf = new StringBuilder(len * 2);
//        for (int j = 0; j < len; j++) {
//            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
//            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
//        }
//        return buf.toString();
        String s=sb.toString();
        s=s.substring(0,s.length()-1);
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
