package com.shadeien.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "CONSUME_REALITY_QUERY_EXTEND_V_1")
//    @KafkaListener(topics = "kafkaLearning", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) throws Exception {
//        List<Dml> dmls = MessageUtil.parse4Dml("destinbation", data.value());
        if (data.timestamp() >= 1562038921000l && data.timestamp() <= 1562047943000l)
            log.info("dmls:{}", data);
//        if (data.key().contains("22739000000003043")) {
//            log.info("get:{}", data);
//        }
//        acknowledgment.acknowledge();
//        if (data.offset() == 7) {
//            acknowledgment.acknowledge();
//        } else {
//            throw new Exception();
//        }
    }


}
