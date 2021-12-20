package com.jatte;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TestConsumer_2_2 {

    private Consumer consumer;

    public void consume() {
        try (FileInputStream fis = new FileInputStream("KafkaClient/src/main/resources/kafka_consumer_2.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            consumer = new KafkaConsumer(properties);
            consumer.subscribe(Arrays.asList("test"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("@@@@@@@@@@@@@##############################################################");
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished consuming ");
    }

    public static void main(String[] args) {
        TestConsumer_2_2 tp = new TestConsumer_2_2();
        tp.consume();
    }
}
