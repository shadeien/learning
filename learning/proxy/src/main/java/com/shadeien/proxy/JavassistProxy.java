package com.shadeien.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class JavassistProxy {

    public static void main(String[] args) throws Exception {
        IUserDao target = new UserDao();
        System.out.println(target);
        IUserDao userDao = (IUserDao)testJavassistFactoryProxy(target);
        userDao.save();

        UserDao proxy = (UserDao)testJavassistDefineClass(target);
        proxy.save();
    }

    public static Object testJavassistDefineClass(Object target) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        String name = target.getClass().getName();
        CtClass ctClass = classPool.makeClass(name+"JavassistProxy");
        ctClass.setSuperclass(classPool.getCtClass(name));
//        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
        ctClass.addMethod(CtMethod.make("public void save() {\n" +
                "        System.out.println(\"before\");\n" +
                "        super.save();\n" +
                "        System.out.println(\"after\");\n" +
                "    }", ctClass));
        ctClass.writeFile("G:\\");
        Object obj = ctClass.toClass().newInstance();
        System.out.println(obj);
        return obj;
    }

    public  static Object testJavassistFactoryProxy(Object target) throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(target.getClass());
        Class aClass = proxyFactory.createClass();
        ProxyObject proxyObject = (ProxyObject)aClass.newInstance();
        System.out.println(proxyObject);

        proxyObject.setHandler((self, thisMethod, proceed, args) -> {
            System.out.println("before");
            Object str = thisMethod.invoke(target, args);
            System.out.println("after");

            return str;
        });

        return proxyObject;
    }
}
