package com.pet.stub_service.service;

import com.pet.stub_service.kafka.KafkaBatchProducer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class NumberService {

    private final KafkaBatchProducer producer;
    private final Queue<String> numbers = new ConcurrentLinkedQueue<>();

    public NumberService(KafkaBatchProducer producer) {
        this.producer = producer;
    }

    public void save(String id) {

        numbers.add(id);

        if (numbers.size() >= 100) {
            List<String> batch = new ArrayList<>(numbers);
            numbers.clear();
            sendToKafka(batch);
        }

    }

    public void sendToKafka(List<String> numbers) {
        producer.sendMany(numbers);
    }
}
