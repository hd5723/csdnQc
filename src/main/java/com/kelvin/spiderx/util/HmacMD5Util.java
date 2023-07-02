package com.kelvin.spiderx.util;

import cn.hutool.crypto.digest.HMac;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/***
 * @title HmacMD5Util
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/7/2 17:10
 **/
public class HmacMD5Util {

    public static void main(String[] args) throws Exception {
        String testStr = "HTTP上传输的内容";

        // 此处密钥如果有非ASCII字符，考虑编码
        byte[] key = "!@#123KK".getBytes();

        HMac mac = new HMac("HmacMD5", key);
        System.out.println(mac.digestHex(testStr));

        mac = new HMac("HmacSM3", key);
        System.out.println(mac.digestHex(testStr));

        hmacMD5(key,testStr.getBytes());
    }

    public static void hmacMD5(byte[] key,byte[] content) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key, "HmacMD5");
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(skey);
        mac.update(content);
        byte[] result = mac.doFinal();
        System.out.println(new BigInteger(1, result).toString(16));
    }

}
