package tinyurl;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import tinyurl.service.CreateTinyUrl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProducer {

    private Producer<String, CreateTinyUrl> p;

    private final static String CREATE_TINY_URL_IN = "create_tiny_url_in";


    public void post() {
        try (FileInputStream fis = new FileInputStream("url-service/src/main/resources/test_createTinyUrlPublisher.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            p = new KafkaProducer(properties);
            CreateTinyUrl createTinyUrl = new CreateTinyUrl();
            createTinyUrl.setLongUrl("http://www.cnn.com");
            p.send(new ProducerRecord<>(CREATE_TINY_URL_IN, "542", createTinyUrl));
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestProducer tp = new TestProducer();
        tp.post();
    }
}
