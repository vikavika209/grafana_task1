package com.pet.stub_service.kafka;

import com.pet.stub_service.component.NumbersStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@Slf4j
public class KafkaNumbersConsumer {

    private final NumbersStorage storage;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaNumbersConsumer(NumbersStorage storage) {
        this.storage = storage;
    }

    @KafkaListener(topics = "jmeter-events", groupId = "stub-service")
    public void onMessage(byte[] payload) {
        log.info("Запрос на чтение из Kafka");
        try {
            List<String> ids = objectMapper.readValue(
                    payload,
                    new TypeReference<>() {
                    }
            );

            ids.forEach(storage::add);
            log.info("Получено: {} ids",  ids.size());

        } catch (Exception e) {
            log.error("Ошибка получения");
            throw new RuntimeException("Failed to deserialize Kafka batch", e);
        }
    }
}
