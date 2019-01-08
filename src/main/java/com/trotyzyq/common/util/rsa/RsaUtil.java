package com.trotyzyq.common.util.rsa;

/**
 * @Author trotyzyq
 * @Time 2018/9/28.
 */
public class RsaUtil {
    public static String sign(String content,String key){
       return RsaSignature.rsaSign(content+System.currentTimeMillis(), key);
    }
}
