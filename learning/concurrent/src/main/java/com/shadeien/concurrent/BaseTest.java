package com.shadeien.concurrent;

import java.util.*;

public class BaseTest {

    public static void main(String[] args) {
        String.valueOf(9L);

        Person p = new Person();
        p.setName("a");

        modifyPersonName(p);
        System.out.println("after name: "+p.getName());

        int num = 0;
        modifyNum(num);
        System.out.println("after num: "+num);

        Person p1 = p;
        p1.setName("b");
        System.out.println("p:"+ p.getName());
        System.out.println("p1:"+ p1.getName());

        HashMap map = new HashMap();
        map.put(null, "a");
        map.put(null, "b");
        map.put("a", "a");
        map.put("b", "a");
        map.put("vc", "a");
        map.put("c", "a");

        map.size();

        Iterator<Map.Entry> iterator= map.entrySet().iterator();

        System.out.println("-------hashmap entrySet start---------");
        while(iterator.hasNext())
        {
            Map.Entry entry = iterator.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("-------hashmap entrySet end---------");

        Iterator iterator1 = map.keySet().iterator();
        System.out.println("-------hashmap keySet start---------");
        while(iterator1.hasNext())
        {
            Object key = iterator1.next();
            System.out.println(key+":"+map.get(key));
        }
        System.out.println("-------hashmap keySet end---------");

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

    public static void modifyPersonName(Person person) {
        person = new Person();
        person.setName("abc");
        System.out.println("name: "+person.getName());
    }

    public static void modifyNum(int num) {
        num = 2;
        System.out.println("num: "+num);
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
