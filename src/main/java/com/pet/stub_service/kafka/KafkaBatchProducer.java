package com.pet.stub_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@Slf4j
public class KafkaBatchProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaBatchProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC = "jmeter-events";

    public void sendMany(List<String> ids) {
        log.info("Передано на отправку: {} ids", ids.size());
        try {
            byte[] payload = objectMapper.writeValueAsBytes(ids);
            kafkaTemplate.send(TOPIC, payload);
            log.info("Отправка успешна");
        } catch (Exception e) {
            log.error("Отправка неуспешна");
            throw new RuntimeException("Failed to serialize batch", e);
        }
    }
}
