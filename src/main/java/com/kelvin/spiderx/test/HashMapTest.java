package com.kelvin.spiderx.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/***
 * @title HashMapTest
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/8/18 15:43
 **/
public class HashMapTest {

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        HashMap map = new HashMap<>();

        //获取HashMap整个类
        Class<?> hashMapClszz = map.getClass();
        //获取指定属性，也可以调用getDeclaredFields()方法获取属性数组
        Field threshold =  hashMapClszz.getDeclaredField("threshold");
        //将目标属性设置为可以访问
        threshold.setAccessible(true);

        Field loadFactor = hashMapClszz.getDeclaredField("loadFactor");
        loadFactor.setAccessible(true);

        //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
        Method capacity = hashMapClszz.getDeclaredMethod("capacity");
        //设置目标方法为可访问
        capacity.setAccessible(true);

        //打印刚初始化的HashMap的元素数量、阈值、容量
        System.out.println("map里的元素数量:" +  map.size() );
//        System.out.println("map所占的空间大小:" +  map.capacity() );
        System.out.println("map触发扩容的阈值:" +  threshold.get(map) );
        System.out.println("map触发扩容的真实阈值:" +  loadFactor.get(map) );
        System.out.println("map的容量:" +  capacity.invoke(map) );

        System.out.println("------------------------------------");
        for (int i = 0; i < 15; i++) {
            map.put(i , i);
        }

        System.out.println("map里的元素数量:" +  map.size() );
//        System.out.println("map所占的空间大小:" +  map.capacity() );
        System.out.println("map触发扩容的阈值:" +  threshold.get(map) );
        System.out.println("map触发扩容的真实阈值:" +  loadFactor.get(map) );
        System.out.println("map的容量:" +  capacity.invoke(map) );
        System.out.println("------------------------------------");

    }


}
