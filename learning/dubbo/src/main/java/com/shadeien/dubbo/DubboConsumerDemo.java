package com.shadeien.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumerDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config-dubbo-consumer.xml");
        context.start();
        IComplexModelService bean = context.getBean(IComplexModelService.class);
        bean.save(new Model());
    }
}
