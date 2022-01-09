package com.jatte;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class TestConsumer_1_1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer_1_1.class);

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
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
//                    System.out.println("Partitions assigned ");
//                    consumer.assignment().stream().forEach(p -> System.out.print(p + ","));
//                    System.out.println();
                    int batchSize = records.count();
                    if (batchSize != 0) {
                        int recordCount = 1;
                        var partitions = records.partitions();
                        for (TopicPartition topicPartition : partitions) {
                            for (ConsumerRecord<String, String> record : records.records(topicPartition)) {
                                System.out.printf("At %s, partition=%s, offset=%d, key=%s, value=%s, %s of batch size %s%n", DateTime.now(), topicPartition, record.offset(), record.key(), record.value(), recordCount, batchSize);
                                if (Integer.parseInt(record.value()) % 1000 == 0) {
                                    Thread.sleep(sleep);
                                }
                                consumer.commitSync(Map.of(topicPartition, new OffsetAndMetadata(record.offset() + 1, "Committing from " + this.getClass().getCanonicalName() + " at " + DateTime.now())));
                            }
                        }
                    } else {
//                        System.out.println("Nothing to consume here");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                LOGGER.info("I caught some error ", e);
            } finally {
                consumer.close();
            }
        }
    }

    private void sleep() {
        try {
            System.out.println("Sleeping for 5 seconds before new instance ");
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestConsumer_1_1 tp = new TestConsumer_1_1();
        tp.consume();
    }
}
