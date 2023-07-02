package com.kelvin.spiderx.common;

import com.kelvin.spiderx.entity.BlogInfoDetail;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @title Common
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/7/2 14:45
 **/
public class Common {

    public static volatile ConcurrentHashMap<String , List<BlogInfoDetail>> csdnblogdataCache = new ConcurrentHashMap();

}
