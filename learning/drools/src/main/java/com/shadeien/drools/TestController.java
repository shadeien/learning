package com.shadeien.drools;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Resource
    private KieContainer kieContainer;

//    @Resource
//    private KieSession kieSession;

    String[] data = {"房地产", "游戏", "P2P", "教育"};

    @RequestMapping("/test")
    public void test(){
        KieSession kieSession = kieContainer.newKieSession();
        List<String> conditionList = Arrays.asList(data[2]);

        List<Refuse> sourceList = new ArrayList<>();
        Refuse refuse1 = new Refuse();
        refuse1.setAge(1);
        refuse1.setCondition1(Arrays.asList(data[0], data[2]));
        sourceList.add(refuse1);

        Refuse refuse2 = new Refuse();
        refuse2.setAge(2);
        refuse2.setCondition1(Arrays.asList(data[1], data[3]));
        sourceList.add(refuse2);

        kieSession.insert(sourceList);
        kieSession.insert(conditionList);
        RefuseResult refuseResult = new RefuseResult();
        kieSession.insert(refuseResult);

        int i = kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("test"));
        log.info("rule num:{}, result:{}", i, refuseResult);
        kieSession.dispose();
    }


    @RequestMapping("/test1")
    public void test1(){
        long start = System.currentTimeMillis();
        System.out.println("-----start-------");
        KieSession kieSession = kieContainer.newKieSession();
        System.out.println(System.currentTimeMillis() - start);
        List<String> conditionList = Arrays.asList(data[1]);

        List<Refuse> sourceList = new ArrayList<>();
        Refuse refuse1 = new Refuse();
        refuse1.setAge(1);
        refuse1.setCondition1(Arrays.asList(data[1], data[2]));
        sourceList.add(refuse1);

        Refuse refuse2 = new Refuse();
        refuse2.setAge(2);
        refuse2.setCondition1(Arrays.asList(data[1], data[3]));
        sourceList.add(refuse2);

        kieSession.insert(sourceList);
        kieSession.insert(conditionList);
        RefuseResult refuseResult = new RefuseResult();
        kieSession.insert(refuseResult);

        int i = kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("test"));
        log.info("rule num:{}, result:{}", i, refuseResult);
        kieSession.dispose();
    }
}
