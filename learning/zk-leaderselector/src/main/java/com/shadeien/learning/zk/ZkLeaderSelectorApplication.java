package com.shadeien.learning.zk;


import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZkLeaderSelectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkLeaderSelectorApplication.class, args);
    }

    @Autowired
    CuratorFramework curatorFramework;

    @Bean
    public MyLeaderSelectorListenerAdapter myLeaderSelectorListenerAdapter() {
        MyLeaderSelectorListenerAdapter myLeaderSelectorListenerAdapter = new MyLeaderSelectorListenerAdapter(curatorFramework,"/zk-leader-selector", "zk-leader-selector");
        myLeaderSelectorListenerAdapter.start();

        return myLeaderSelectorListenerAdapter;
    }
}
