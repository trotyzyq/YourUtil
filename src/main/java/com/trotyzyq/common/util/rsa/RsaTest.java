package com.trotyzyq.common.util.rsa;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RsaTest {
    /** *测试公钥加密，私钥解密*/
    @Test
    public void testEn(){
        String enStr = RsaSignature.rsaEncrypt("1231231",RsaConstant.serverPublicKey);
        System.out.println(enStr);
        String deStr = RsaSignature.rsaDecrypt(enStr,RsaConstant.serverPrivateKey);
        System.out.println(deStr);
    }

    /** 测试私钥加签，公钥解签**/
    @Test
    public void testSign(){
        Map<String, String> map = new HashMap();
        map.put("uge","12");
        map.put("timeStamp","2018412568544");
        String sign = RsaSignature.rsa256Sign(RsaSignature.getSignCheckContentV2(map),RsaConstant.serverPrivateKey);
        map.put("sign",sign);
        System.out.println(JSON.toJSONString(map));

        Map<String, String> map2 = new HashMap();
        map2.put("uge","121");
        map2.put("timeStamp","2018412568544");
        String sign2 = RsaSignature.rsa256Sign(RsaSignature.getSignCheckContentV2(map),RsaConstant.serverPrivateKey);
        map2.put("sign",sign2);
        boolean success = RsaSignature.rsaCheckV2(map2,RsaConstant.serverPublicKey);
        System.out.println(success);
//        String enStr = RsaSignature.rsa256Sign("1231231",RsaConstant.serverPrivateKey);
//        System.out.println(enStr);
//        String deStr = RsaSignature.rsaDecrypt(enStr,RsaConstant.serverPrivateKey);
//        System.out.println(deStr);
    }
}
