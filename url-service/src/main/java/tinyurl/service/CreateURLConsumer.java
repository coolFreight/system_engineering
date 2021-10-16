package tinyurl.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class CreateURLConsumer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreateURLConsumer.class);

    private final static String CREATE_TINY_URL_IN = "create_tiny_url_in";


    private SimpleTinyUrlResource tinyUrlService;

    public void setTinyUrlService( SimpleTinyUrlResource tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    private Consumer consumer;

    public void consume() {
        try (FileInputStream fis = new FileInputStream("url-service/src/main/resources/create_tinyurl_consumer.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            consumer = new KafkaConsumer(properties);
            consumer.subscribe(Arrays.asList(CREATE_TINY_URL_IN));
            while (true) {
                ConsumerRecords<String, CreateTinyUrl> records = consumer.poll(Duration.ofMillis(100));
                if(records != null) {
                    for (ConsumerRecord<String, CreateTinyUrl> record : records) {
                        CreateTinyUrl createTinyUrl = record.value();
                        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), createTinyUrl);
                        if (createTinyUrl != null) {
                            LOGGER.info("Found a valid tinyUrl to publish {} ", createTinyUrl);
                            tinyUrlService.createTinyUrl(record.value());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished consuming ");
    }

    public void init(){
        consume();
    }
}
