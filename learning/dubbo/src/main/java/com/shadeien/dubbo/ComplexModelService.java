package com.shadeien.dubbo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComplexModelService implements IComplexModelService {
    @Override
    public void save(Model model) {
        log.info("save:{}", JSON.toJSONString(model));
    }
}
