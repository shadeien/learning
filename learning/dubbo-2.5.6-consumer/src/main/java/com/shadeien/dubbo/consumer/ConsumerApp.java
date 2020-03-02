//package com.shadeien.dubbo.consumer;
//
//import com.shadeien.dubbo.IComplexModelService;
//import com.shadeien.dubbo.Model;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class ConsumerApp {
//    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config-dubbo-consumer.xml");
//        context.start();
//        IComplexModelService bean = context.getBean(IComplexModelService.class);
//        String res = bean.save(new Model());
//        System.out.println("res:"+res);
//    }
//}
