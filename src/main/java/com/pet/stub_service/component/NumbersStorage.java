package com.pet.stub_service.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class NumbersStorage {
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();

    public void add(String id) {
        queue.add(id);
    }

    public List<String> drainAll() {
        List<String> result = new ArrayList<>();
        String v;
        while ((v = queue.poll()) != null) {
            result.add(v);
        }
        return result;
    }
}
