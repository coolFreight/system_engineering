package tinyurl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class CreateTinyUrlSerializer implements Serializer<CreateTinyUrl>, Deserializer<CreateTinyUrl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTinyUrlSerializer.class);

    private ObjectMapper mapper =  new ObjectMapper();

    @Override
    public CreateTinyUrl deserialize(String topic, byte[] data) {
        CreateTinyUrl createTinyUrl = null;
        try {
            createTinyUrl = mapper.readValue(data, CreateTinyUrl.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createTinyUrl;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, CreateTinyUrl createTinyUrl) {
        try {
            return mapper.writeValueAsBytes(createTinyUrl);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void close() {
        LOGGER.info("Should be doing some deserialized clean up[");
    }
}
