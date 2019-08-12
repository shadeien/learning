package com.shadeien.concurrent;

import java.util.*;

public class BaseTest {

    public static void main(String[] args) {
        Person p = new Person();
        p.setName("a");

        Person p1 = p;
        p1.setName("b");
        System.out.println("p:"+ p.getName());
        System.out.println("p1:"+ p1.getName());

        HashMap map = new HashMap();
        map.put(null, "a");
        map.put(null, "a");

        map.size();

        Iterator<Map.Entry> iterator= map.entrySet().iterator();

        while(iterator.hasNext())
        {
            Map.Entry entry = iterator.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        Hashtable hashtable = new Hashtable();
        hashtable.put("c", "a");

        Enumeration en = hashtable.elements();
        while (en.hasMoreElements()) {
            Object ob = en.nextElement();
            System.out.println(ob);
        }

//        Integer h=new Integer(59);
//        int g=59;
//        System.out.println(g==h);//输出 true
    }

    static class Person {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
