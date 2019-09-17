package com.shadeien.proxy;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.net.URL;
import java.net.URLClassLoader;

@Slf4j
public class ReflectionsExample {
    public static void main(String[] args) throws Exception {
        String packageName = "commonindustry";
        String url = "http://192.168.6.166:8081/nexus/content/groups/cit3/com/wwwarehouse/commonindustry-common/1.0-SNAPSHOT/commonindustry-common-1.0-20190911.093700-463.jar";
        URL uri = new URL(url);
        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{uri});
//        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{uri}, Thread.currentThread().getContextClassLoader());
        ClassPool classPool = new ClassPool();
        classPool.appendClassPath(new LoaderClassPath(myClassLoader));

        InterfaceScanner scanner = new InterfaceScanner();
        scanner.setLoaderPool(classPool);
        Reflections.log = log;
        Reflections reflections = new Reflections( scanner, uri);
        scanner.setLoaderPool(null);
    }
}
