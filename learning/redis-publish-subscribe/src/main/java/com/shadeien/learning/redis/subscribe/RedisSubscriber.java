package com.shadeien.learning.redis.subscribe;

import com.shadeien.learning.redis.listener.RedisListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisSubscriber {

    @Autowired
    RedisMessageListenerContainer container;

    @Autowired
    RedisListener redisListener;

    /**
     * redis订阅用户数据
     * @param channel
     */
    public void subscribe(String channel) {
        log.info("subscribe channel:{}", channel);
        ChannelTopic topic = new ChannelTopic(channel);
        container.addMessageListener(redisListener, topic);
    }

    /**
     * 取消reds订阅
     * @param channel
     */
    public void unsubscribe(String channel) {
        log.info("unsubscribe channel:{}", channel);
        ChannelTopic topic = new ChannelTopic(channel);
        container.removeMessageListener(redisListener, topic);
    }

}
