package com.jatte;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.joda.time.DateTime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class TestConsumer_1_1_no_restart {

    private Consumer consumer;

    public void consume() {
        Properties properties = null;
        while (true) {
            try (FileInputStream fis = new FileInputStream("KafkaClient/src/main/resources/kafka_consumer_1.properties")) {
                properties = new Properties();
                properties.load(fis);
                int sleep = Integer.parseInt(properties.getProperty("consumer.sleep", "200"));
                consumer = new KafkaConsumer(properties);
                consumer.subscribe(Arrays.asList("test"));
//                System.out.println("Partitions assigned "+consumer.toString());
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
//                    consumer.assignment().stream().forEach(p -> System.out.print(p + ","));
//                    System.out.println();
                    int batchSize = records.count();
                    if (batchSize != 0) {
                        var partitions = records.partitions();
                        for (TopicPartition topicPartition : partitions) {
                            int recordCount = 1;
                            for (ConsumerRecord<String, String> record : records.records(topicPartition)) {
                                System.out.printf("At %s, partition=%s, offset=%d, key=%s, value=%s, %s of batch size %s%n",  DateTime.now(), topicPartition, record.offset(), record.key(), record.value(), recordCount, batchSize);
                                consumer.commitSync(Map.of(topicPartition, new OffsetAndMetadata(record.offset() + 1, "Committing from "+this.getClass().getCanonicalName()+" at "+ DateTime.now())));
                                recordCount++;
                            }
//                            Thread.sleep(200);
                        }
                    } else {
//                        System.out.println("Nothing to consume here");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep() {
        try {
            System.out.println("Sleeping for 5 seconds before new instance ");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestConsumer_1_1_no_restart tp = new TestConsumer_1_1_no_restart();
        tp.consume();
    }
}
