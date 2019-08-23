package com.shadeien.gateway;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        StudentBean stu = null;
        System.out.println("postProcessBeforeInitialization 对初始化之前的Bean进行处理,将Bean的成员变量的值修改了");
        if("name".equals(beanName) || bean instanceof StudentBean) {
            stu = (StudentBean) bean;
            stu.setName("postProcessBeforeInitialization");
        }
        return stu;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(bean + beanName);
        return bean;
//        StudentBean stu = null;
//        System.out.println("postProcessAfterInitialization对初始化之后的Bean进行处理,将Bean的成员变量的值修改了");
//        if("name".equals(beanName) || bean instanceof StudentBean) {
//            stu = (StudentBean) bean;
//            stu.setName("postProcessAfterInitialization");
//        }
//
//        return stu;
    }
}
