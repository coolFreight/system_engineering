package tinyurl.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class CreateTinyUrlPublisher {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreateTinyUrlPublisher.class);

    private Producer p;
    private final static String CREATE_TINY_URL_OUT = "create_tiny_url_out";
    private SimpleTinyUrlResource tinyUrlService;


    public void setTinyUrlService(SimpleTinyUrlResource tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    public void init() throws IOException {
        try (FileInputStream fis = new FileInputStream("order_service/src/main/resources/kafka_producer.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            p = new KafkaProducer(properties);
        }
    }

    public Future<RecordMetadata> publishOrderEvent(CreateTinyUrl createTinyUrl) {
        LOGGER.info("Published order event of for order {} ", createTinyUrl.getLongUrl());
        return p.send(new ProducerRecord<>(CREATE_TINY_URL_OUT, createTinyUrl.getLongUrl(), createTinyUrl));
    }
}


