package com.shadeien.hessain;

import com.alibaba.fastjson.JSON;
import com.shadeien.dubbo.Model;

import java.net.MalformedURLException;
import java.util.Collections;

public class ComplextModelServiceTest {
    public static void main(String[] args) throws MalformedURLException {
        User user = new User();
        user.setAge(11);
        user.setName("11");
        Model<User> model = new Model<>();
        model.setList(Collections.singletonList(user));

        JSON.toJSONString(model);
    }
}
