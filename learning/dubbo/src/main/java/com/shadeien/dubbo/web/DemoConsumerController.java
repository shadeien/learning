package com.shadeien.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shadeien.dubbo.IComplexModelService;
import com.shadeien.dubbo.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoConsumerController {

//    @Reference(version = "${spring.dubbo.usercenter.version}",
//            group = "${spring.dubbo.usercenter.group}",
////            url = "hessian://192.168.6.165:9091",
//            check = true)
//    UserInfoService userInfoService;

//    @RequestMapping("/sayHello")
//    public Object sayHello() throws Exception {
//        return userInfoService.getUserLoginCacheByToken("aaaaa");
//    }

    @Reference
    IComplexModelService complexModelService1;

    @RequestMapping("/hello")
    public Object hello() {
        complexModelService1.save(new Model());
        return "OK";
    }
}
