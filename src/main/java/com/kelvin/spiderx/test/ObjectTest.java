package com.kelvin.spiderx.test;

import java.util.Objects;

/***
 * @title ObjectTest
 * @desctption Object
 * @author kelvin
 * @create 2023/7/15 12:06
 **/
public class ObjectTest {

    public static void main(String[] args) {
        // 创建一个对象
        Person person = new Person("Kelvin", 25);
        Person newPerson = new Person("Kelvin", 25);
        // 调用toString()方法
/*        String str = person.toString();
        System.out.println("toString(): " + str);*/

        // 调用equals()方法
 /*       boolean isEqual = person.equals(newPerson);
        System.out.println("equals(): " + isEqual);
        System.out.println( System.identityHashCode(person) );
        System.out.println( System.identityHashCode(newPerson) );*/


        // 调用hashCode()方法
        int hashCode = person.hashCode();
        System.out.println("hashCode(): " + hashCode);

        // 调用hashCode()方法
        int newHashCode = newPerson.hashCode();
        System.out.println("newHashCode(): " + newHashCode);

 /*
        // 调用getClass()方法
        Class<?> clazz = person.getClass();
        System.out.println("getClass(): " + clazz.getName());*/

    }


}


class Person extends Object{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return name.equals(other.name) && age == other.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}