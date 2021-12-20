package com.jatte;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

public class TestProducer {

    private Producer p;

    public void post() {
        try (FileInputStream fis = new FileInputStream("KafkaClient/src/main/resources/kafka_producer.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            p = new KafkaProducer(properties);
            int count = 0;
            while(true) {
                var key = UUID.randomUUID().toString().concat("___"+count);
                p.send(new ProducerRecord<>("test", null,  count+""));
                count++;
                Thread.sleep(3000);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestProducer tp = new TestProducer();
        tp.post();
    }
}
