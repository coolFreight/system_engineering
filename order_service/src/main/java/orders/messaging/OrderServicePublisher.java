package orders.messaging;

import orders.model.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class OrderServicePublisher {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrderServicePublisher.class);

    private Producer p;
    private final static String ORDERS_TOPIC = "orders";

    public void init() throws IOException {
        try (FileInputStream fis = new FileInputStream("order_service/src/main/resources/kafka_producer.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            p = new KafkaProducer(properties);
        }
    }

    public Future<RecordMetadata> publishOrderEvent(Order order) {
        LOGGER.info("Published order event of for order {} ", order.getOrderId());
        return p.send(new ProducerRecord<>(ORDERS_TOPIC, order.getOrderId()+"", order));
    }
}


