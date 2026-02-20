package com.pet.stub_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class NumberService {

    private final List<Integer> numbers = new CopyOnWriteArrayList<>();

    public void save(String id) {
        Integer number = Integer.valueOf(id);
        numbers.add(number);
    }

    public String getAllAsString() {
        return numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
