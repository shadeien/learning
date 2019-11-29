package com.shadeien.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
public class KafkaMain {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaMain.class, args);
        KafkaTemplate kafkaTemplate = context.getBean(KafkaTemplate.class);
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("dev_internal_mcprotocol_request_topic", "test");
        }
//        kafkaTemplate.send("REPEAT_ADD_CARD_AUTHORITY", "{\"authorityType\":3,\"authorizeUserId\":10001101,\"batch\":false,\"beauthorizedUserId\":10009345,\"businessUnitId\":910002024,\"cardId\":42439000000001005,\"invalidDate\":1558483920000,\"validDate\":1558397700000}");

//        Thread.sleep(100000);
//        context.registerShutdownHook();
//        context.close();
    }

//    @Bean
//    public RetryingMessageListenerAdapter retryingMessageListenerAdapter(MessageListener<String, String> messageListener, RetryTemplate retryTemplate) {
//        RetryingMessageListenerAdapter retryingMessageListenerAdapter = new RetryingMessageListenerAdapter(messageListener, retryTemplate);
//
//        return retryingMessageListenerAdapter;
//    }

    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, RetryTemplate retryTemplate, ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure(concurrentKafkaListenerContainerFactory, consumerFactory);
        concurrentKafkaListenerContainerFactory.setRetryTemplate(retryTemplate);

        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public RetryTemplate retryTemplate(KafkaProperties properties) {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }
}
