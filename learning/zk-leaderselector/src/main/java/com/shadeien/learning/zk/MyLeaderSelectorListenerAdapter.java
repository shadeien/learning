package com.shadeien.learning.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class MyLeaderSelectorListenerAdapter extends LeaderSelectorListenerAdapter implements Closeable {
    static final Logger logger = LoggerFactory.getLogger(MyLeaderSelectorListenerAdapter.class);
    private String name;
    private LeaderSelector leaderSelector;
    static boolean isLeader = false;

    public MyLeaderSelectorListenerAdapter(CuratorFramework client, String path, String name) {
        this.name = name;
        leaderSelector = new LeaderSelector(client, path, this);
        leaderSelector.autoRequeue();
    }

    public void start() {
        leaderSelector.start();
    }

    @Override
    public void close() {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws InterruptedException {
        logger.info("{}:I am leader...", name);
        isLeader = true;
        while (isLeader) {
            Thread.sleep(3000);
        }
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        logger.info("{}:Connection state changed...", name);
        isLeader = false;
        super.stateChanged(client, newState);
    }
}
