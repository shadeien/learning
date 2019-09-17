package com.shadeien.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.MethodParametersAttribute;
import org.reflections.scanners.AbstractScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

@Component
public class InterfaceScanner extends AbstractScanner {
    private Logger logger = LoggerFactory.getLogger(InterfaceScanner.class);
    private ClassPool loaderPool;

    public void setLoaderPool(ClassPool loaderPool) {
        this.loaderPool = loaderPool;
    }

    @Override
    public void scan(Object cls) {
        if (null == loaderPool)
            return;
        String className = getMetadataAdapter().getClassName(cls);
        try {
            CtClass ctClass = loaderPool.get(className);
            if (null != ctClass && ctClass.isInterface() && !ctClass.isAnnotation()) {
                List<MethodInfo> methods = getMetadataAdapter().getMethods(cls);
                methods.stream().forEach(methodInfo -> {
                    List parameterNames = getMetadataAdapter().getParameterNames(methodInfo);
                    MethodParametersAttribute methodParameters = (MethodParametersAttribute)methodInfo.getAttribute("MethodParameters");
                    for (int i = 0; i < parameterNames.size(); i++) {
                        String str = methodParameters.getConstPool().getUtf8Info(methodParameters.name(i));
                        logger.info("class:{} name:{} params:{} {}", className, methodInfo.getName(), parameterNames.get(i), str);
                    }
                });
            }
        } catch (Throwable e) {
            if (e instanceof NoClassDefFoundError) {
                logger.error("NoClassDefFoundError:{}", className);
            } else {
                logger.error("", e);
            }
        }
    }

    private void jdkReflect(String className, ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className, false, classLoader);
        if (null != aClass && aClass.isInterface() && !aClass.isAnnotation()) {
            Method[] declaredMethods = aClass.getDeclaredMethods();
            Arrays.stream(declaredMethods).forEach(method -> {
                Parameter[] params = method.getParameters();
                logger.info("{}", params);
            });
        }
    }
}
