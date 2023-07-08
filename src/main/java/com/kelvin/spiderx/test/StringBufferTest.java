package com.kelvin.spiderx.test;

import com.kelvin.spiderx.common.ObjectCommon;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

/***
 * @title StringBufferTest
 * @desctption StringBufferTest
 * @author LTF
 * @create 2023/7/7 22:35
 **/
@Slf4j
public class StringBufferTest {

    public static void main(String[] args) {
       /* StringBuffer stringBuffer = new StringBuffer();
        log.info("内存地址:{}" , ObjectCommon.toString(stringBuffer) );
        System.out.println("---------------------------------------");

        stringBuffer.append("Kelvin");
        log.info("内存地址:{}" , ObjectCommon.toString(stringBuffer) );
        System.out.println("---------------------------------------");

        stringBuffer.append("!");
        log.info("内存地址:{}" , ObjectCommon.toString(stringBuffer) );
        System.out.println("---------------------------------------");*/



        StringBuffer stringBuffer = new StringBuffer(10);
        log.info("值长度:{}" , stringBuffer.length());
        log.info("所占空间长度:{}" , stringBuffer.capacity());
        System.out.println("---------------------------------------");

        stringBuffer.append("Kelvin");
        log.info("值长度:{}" , stringBuffer.length());
        log.info("所占空间长度:{}" , stringBuffer.capacity());
        System.out.println("---------------------------------------");

        stringBuffer.append("!");
        log.info("值长度:{}" , stringBuffer.length());
        log.info("所占空间长度:{}" , stringBuffer.capacity());
        System.out.println("---------------------------------------");

        stringBuffer.append("12345678901234567890");
        log.info("值长度:{}" , stringBuffer.length());
        log.info("所占空间长度:{}" , stringBuffer.capacity());
        System.out.println("---------------------------------------");

    }

}
