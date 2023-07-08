package com.kelvin.spiderx.test;

import com.kelvin.spiderx.common.ObjectCommon;
import lombok.extern.slf4j.Slf4j;

/***
 * @title StringBufferTest
 * @desctption StringBufferTest
 * @author LTF
 * @create 2023/7/7 22:35
 **/
@Slf4j
public class StringTest {

    public static void main(String[] args) {
        String str = new String();
        log.info("内存地址:{}" , ObjectCommon.toString(str) );
        System.out.println("---------------------------------------");

        str += "Kelvin";
        log.info("内存地址:{}" , ObjectCommon.toString(str) );
        System.out.println("---------------------------------------");

        str += "!";
        log.info("内存地址:{}" , ObjectCommon.toString(str) );
        System.out.println("---------------------------------------");
    }

}
