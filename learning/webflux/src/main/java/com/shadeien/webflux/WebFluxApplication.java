package com.shadeien.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true)
@EnableJpaAuditing
@Slf4j
public class WebFluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

//    @Bean
//    public List<Address> getAddresses() {
//        List<Address> addresses = new ArrayList<>(3);
//        addresses.add(new Address("192.168.6.21", 5672));
//        addresses.add(new Address("192.168.6.22", 5672));
//        addresses.add(new Address("192.168.6.23", 5672));
//
//        return addresses;
//    }
//
//    @Bean
//    public ConnectionFactory getConnectionFactory() throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setUsername("admin");
//        connectionFactory.setPassword("admin");
////        connectionFactory.newConnection(addresses);
//        connectionFactory.useNio();
//
//        return connectionFactory;
//    }
//
//    @Bean
//    @Order(-1)
//    public Sender getSenderOptions(ConnectionFactory connectionFactory, List<Address> addresses) {
//        SenderOptions senderOptions = new SenderOptions()
//                .connectionFactory(connectionFactory)
//                .connectionSupplier(cf -> cf.newConnection(addresses))
//                .resourceManagementScheduler(Schedulers.elastic());
//
//        Sender sender = RabbitFlux.createSender(senderOptions);
////        sender.declare(queue("reactive.queue"));
//        sender.declare(exchange("reactive.exchange"))
//                .then(sender.declare(queue("reactive.queue")))
//                .then(sender.bind(binding("reactive.exchange", "a.b", "reactive.queue")))
//                .subscribe(r -> System.out.println("Exchange and queue declared and bound"));
//
//        return sender;
//    }
//
//    @Bean
//    public ReceiverOptions getReceiverOptions(ConnectionFactory connectionFactory, List<Address> addresses) {
//        ReceiverOptions receiverOptions = new ReceiverOptions()
//                .connectionFactory(connectionFactory)
//                .connectionSupplier(cf -> cf.newConnection(addresses))
//                ;
//
//        Flux<Delivery> inboundFlux = RabbitFlux.createReceiver(receiverOptions)
//                .consumeAutoAck("reactive.queue");
//
//        inboundFlux.subscribe(data -> log.info("get msg:{}", new String(data.getBody())));
//
//        return receiverOptions;
//    }
}
