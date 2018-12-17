package com.shadeien.learning.zk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SelectorController {

    @GetMapping("/demo/down")
    public Object down() {
        log.info("get down");
        MyLeaderSelectorListenerAdapter.isLeader = false;
        return "down";
    }
}
