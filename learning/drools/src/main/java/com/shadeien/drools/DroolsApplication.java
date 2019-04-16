package com.shadeien.drools;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DroolsApplication {
    public static void main(String[] args) {
        try {
            fireFromString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fireNormal() throws Exception {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("session-base");
        try {
            Map<String,String> refuseDate=new HashMap<String, String>();
            Refuse refuse=new Refuse();
            refuse.setAge(11);
            kSession.setGlobal("refuseDate",refuseDate);
            kSession.insert(refuse);
            long start = System.currentTimeMillis();
            int count=kSession.fireAllRules();
            log.info("diff:{}", System.currentTimeMillis() - start);
            System.out.println("规则执行条数：--------"+count);
            System.out.println("规则执行完成--------"+refuse.toString());
            System.out.println(kSession.getGlobals().toString());
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            kSession.dispose();
        }
    }

    public static void fireFromString() throws Exception {
        BufferedReader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("rules/refuse/templatelogic.drl").toURI()));
        String line = null;
        final StringBuffer buffer = new StringBuffer(2048);
        while ((line = reader.readLine()) != null) {
             buffer.append(line);
             buffer.append("\r\n");
        }
        KieHelper helper = new KieHelper();
        helper.addContent(buffer.toString(), ResourceType.DRL);
        KieSession ksession = helper.build().newKieSession();

        Map<String,String> refuseDate=new HashMap<String, String>();
        Refuse refuse=new Refuse();
        refuse.setAge(80);
        ksession.setGlobal("refuseDate",refuseDate);
        ksession.setGlobal("maxAge",60);
        ksession.setGlobal("minAge",22);
        ksession.setGlobal("list", Arrays.asList(1,2,3,100,77));
        ksession.insert(refuse);

        int i = ksession.fireAllRules();
        log.info("{}", refuseDate);
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


