package com.shadeien.kafka;


import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "sit_binlog_au_user_card_authority")
//    @KafkaListener(topics = "kafkaLearning", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, Message> data, Acknowledgment acknowledgment) throws Exception {
        List<Dml> dmls = MessageUtil.parse4Dml("destinbation", data.value());
        log.info("dmls:{}", dmls);
//        acknowledgment.acknowledge();
//        if (data.offset() == 7) {
//            acknowledgment.acknowledge();
//        } else {
//            throw new Exception();
//        }
    }


}
