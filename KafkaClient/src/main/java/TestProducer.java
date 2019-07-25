import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

public class TestProducer {

    private Producer p;

    public void init() {
        Properties properties = new Properties();
        p = new KafkaProducer(properties);
    }
}
