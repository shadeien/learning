package com.shadeien.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;

public class JDKReflect {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://192.168.6.166:8081/nexus/content/groups/cit3/com/wwwarehouse/commonindustry-common/1.0-SNAPSHOT/commonindustry-common-1.0-20190911.093700-463.jar");
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
            Class<?> clazz = Class.forName("com.wwwarehouse.xdw.commonindustry.service.AbnormalHandleService", false, myClassLoader);
//            Method method = clazz.getMethod("autoChargeAgainst", Long.class, Long.class);
            Method method = clazz.getMethod("listGsMapWithFeatureCountByWarehouseUkid", Long.class);
            Parameter[] parameters = method.getParameters();
            System.out.println(parameters);
        } catch (Exception e) {

        }
    }
}
