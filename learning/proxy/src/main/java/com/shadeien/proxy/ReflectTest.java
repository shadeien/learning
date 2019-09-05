package com.shadeien.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReflectTest {
    public static void main(String[] args) {
        List<String>[] ls = new ArrayList[10];
//        ArrayList<String>[] ls1 = new ArrayList<String>[10];// error
        ArrayList<String> list = new ArrayList<String>(3);
        list.add("a");
        list.add("b");
        list.add("v");
        ls[0] = list;
        list.add("c");
        ls[1] = list;
        log.info("{}", ls);

        try {
            testArray();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class<UserDao> userDao = UserDao.class;
        Method[] methods = userDao.getDeclaredMethods();
        log.info("methods:{}", methods);
        Method[] allMethods = userDao.getMethods();
        log.info("allMethods:{}", allMethods);
        try {
            UserDao ins = userDao.newInstance();
            Method m = userDao.getDeclaredMethod("getPublic", String.class);
            m.invoke(ins);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        Class<?> c = String.class;
        try {
            Object ins = c.newInstance();
            System.out.println(ins.getClass());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Constructor<?> con = c.getConstructor(String.class);
            Object abc = con.newInstance("abc");
            System.out.println(abc);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void testArray() throws ClassNotFoundException {

        Class<?> cls = Class.forName("java.lang.String");

        Object array = Array.newInstance(cls,10);

//往数组里添加内容

        Array.set(array,0,"hello");

        Array.set(array,1,"Java");

        Array.set(array,2,"fuck");

        Array.set(array,3,"Scala");

        Array.set(array,4,"Clojure");

//获取某一项的内容

        System.out.println(Array.get(array,3));
        log.info("{}", array);

    }
}
