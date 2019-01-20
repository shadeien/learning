package com.shadeien.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "kafkaLearning")
//    @KafkaListener(topics = "kafkaLearning", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) throws Exception {
        log.info("listen:{}", data);
//        acknowledgment.acknowledge();
        if (data.offset() == 7) {
            acknowledgment.acknowledge();
        } else {
//            throw new Exception();
        }
    }


}
