package com.shadeien.math.copy;

public class TestCopy {

    public  static void main(String[] args) throws Exception{
        Person p1 = new Person("zhangsan",21);
        p1.setAddress("X省", "X市");
        Person p2 = (Person) p1.clone();
        Person p3 = p1;
        System.out.println("p1:"+p1);
        System.out.println("p2:"+p2);
        System.out.println("p3:"+p3);

        p1.display("p1");
        p2.display("p2");
        p3.display("p3");

        p1.setName("lisi");
        p3.setAge(19);
        p2.setAddress("Y省", "Y市");
        System.out.println("将复制之后的对象地址修改：");
        p1.display("p1");
        p2.display("p2");
        p3.display("p3");
    }
}
