package com.shadeien.proxy;

public class OtherUserDao {
    public void save() {
        System.out.println("OtherUserDao----已经保存数据!----");
    }

    public void save1(String a) {
        System.out.println("OtherUserDao----save1---"+a);
    }
}
