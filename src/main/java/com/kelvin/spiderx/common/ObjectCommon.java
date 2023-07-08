package com.kelvin.spiderx.common;

/***
 * @title ObjectCommon
 * @desctption 对象辅助类
 * @author LTF
 * @create 2023/7/7 23:35
 **/
public class ObjectCommon {

    /**
     * 返回 对象的类名称 和 内存身份hashcode
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
