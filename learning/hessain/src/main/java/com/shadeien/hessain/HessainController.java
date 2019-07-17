package com.shadeien.hessain;

import com.shadeien.dubbo.IComplexModelService;
import com.shadeien.dubbo.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

@RestController
public class HessainController {

    @Resource
    IComplexModelService iComplexModelService;

    @GetMapping("/hessian/test")
    public void test() {
        User user = new User();
        user.setAge(11);
        user.setName("11");
        user.setRemark("111111");
        Model<User> model = new Model<>();
        model.setList(Collections.singletonList(user));
        iComplexModelService.save(model);
    }
}
