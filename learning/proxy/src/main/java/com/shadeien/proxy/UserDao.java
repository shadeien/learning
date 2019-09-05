package com.shadeien.proxy;

public class UserDao extends BaseUserDao implements IUserDao {

    private void get() {
        System.out.println("private get");
    }

    void getDefault() {
        System.out.println("default get");
    }

    public void getPublic(String abc) {
        System.out.println("Public get");
    }

    @Override
    public void save() {
        System.out.println("----已经保存数据!----");
    }

    @Override
    void baseSave() {
        System.out.println("UserDao BaseSave");
    }
}
