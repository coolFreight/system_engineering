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

public class TestConsumer_1_2 {

    private Consumer consumer;

    public void consume() {
        try (FileInputStream fis = new FileInputStream("KafkaClient/src/main/resources/kafka_consumer_1.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            consumer = new KafkaConsumer(properties);
            consumer.subscribe(Arrays.asList("test"));
            int count = 0;
            while (true) {
                System.out.println("Partitions assigned ");
                consumer.assignment().stream().forEach(p -> System.out.print(p + ","));
                System.out.println();
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(400));
                System.out.printf("Poll finished with %s records", records.count());
                System.out.println();
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("@@@@@@@@@@@@@##############################################################");
                    System.out.printf("partition = %s, offset = %d, key = %s, value = %s%n", records.partitions(), record.offset(), record.key(), record.value());
                }
                consumer.commitSync();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished consuming ");
    }

    public static void main(String[] args) {
        TestConsumer_1_2 tp = new TestConsumer_1_2();
        tp.consume();
    }
}
