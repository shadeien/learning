package com.shadeien.drools;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class DroolsMain {
    public static void main(String[] args) {
//        try {
//            fireFromString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        normal();
    }

    public static void normal() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("session-base");
        for (int i = 0; i < 10; i++) {
            fireNormal(kSession);
        }
        kSession.dispose();
    }

    public static void fireNormal(KieSession kieSession) {
        String[] data = {"房地产", "游戏", "P2P", "教育"};

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
    }

    public static void fireFromString() throws Exception {
        BufferedReader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("rules/refuse/demo.drl").toURI()));
        String line = null;
        final StringBuffer buffer = new StringBuffer(2048);
        while ((line = reader.readLine()) != null) {
             buffer.append(line);
             buffer.append("\r\n");
        }
        KieHelper helper = new KieHelper();
        helper.addContent(buffer.toString(), ResourceType.DRL);
        long start = System.currentTimeMillis();
        System.out.println("---start------");
        KieSession ksession = helper.build().newKieSession();
        System.out.println("耗时："+(System.currentTimeMillis() - start));

        Map<String,String> refuseDate=new HashMap<String, String>();
        Refuse refuse=new Refuse();
        refuse.setAge(80);
        List<String> targetList = new ArrayList<>();
        targetList.add("教育");
        targetList.add("P2P");
        refuse.setCondition1(targetList);
        ksession.setGlobal("refuseDate",refuseDate);
//        ksession.setGlobal("maxAge",60);
//        ksession.setGlobal("minAge",22);
//        ksession.setGlobal("list", Arrays.asList(1,2,3,100,77));
        List<String> blackList = new ArrayList<>();
        blackList.add("房地产");
        blackList.add("游戏");
        blackList.add("P2P");
        ksession.insert(blackList);
        ksession.insert(refuse);

        start = System.currentTimeMillis();
        System.out.println("---start------");
        int i = ksession.fireAllRules();
        System.out.println("耗时："+(System.currentTimeMillis() - start));
        log.info("num:{}, refuseDate:{}", i, refuseDate);
//        ksession.halt();
        ksession.dispose();
    }

    @Data
    class RuleModel {
        String drl;
    }

    public StatelessKieSession newKieSession(RuleModel rule) {
        StringBuffer rulebuffer = new StringBuffer();
        rulebuffer.append(rule.getDrl());
        if (!checkRule(rulebuffer.toString())) {
            return null;
        }
        KieHelper helper = new KieHelper();
        helper.addContent(rulebuffer.toString(), ResourceType.DRL);
        KnowledgeBaseImpl kieBase = (KnowledgeBaseImpl) helper.build();
        StatelessKieSession kieSession = kieBase.newStatelessKieSession();
        return  kieSession;
    }

    private boolean checkRule(String rule) {
        try {
            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);
            if (kb.hasErrors()) {
                log.warn(kb.getErrors().toString());
                return false;
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}


