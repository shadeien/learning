package com.shadeien.gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CycleTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentBean student = (StudentBean) context.getBean("student");
        //Bean的使用
        student.play();
        System.out.println(student);
        Student student1 = (Student) context.getBean("student1");
        System.out.println(student1);
        Student1 student2 = (Student1) context.getBean("student2");
        //关闭容器
        ((ClassPathXmlApplicationContext) context).registerShutdownHook();
//        ((AbstractApplicationContext) context).close();
    }
}
