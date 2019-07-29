import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

public class TestProducer {

    private Producer p;

    public void post() {
        try (FileInputStream fis = new FileInputStream("KafkaClient/src/main/resources/kafka_producer.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            p = new KafkaProducer(properties);
            p.send(new ProducerRecord<>("test", "4", "from intelli-j "+ LocalDateTime.now()));
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
