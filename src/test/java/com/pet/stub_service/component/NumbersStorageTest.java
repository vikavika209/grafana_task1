package com.pet.stub_service.component;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumbersStorageTest {

    @Test
    void add_shouldStoreValue() {
        NumbersStorage storage = new NumbersStorage();

        storage.add("id-1");
        List<String> drained = storage.drainAll();

        assertEquals(List.of("id-1"), drained);
    }

    @Test
    void drainAll_shouldReturnAllValuesAndClearQueue() {
        NumbersStorage storage = new NumbersStorage();

        storage.add("id-1");
        storage.add("id-2");
        storage.add("id-3");

        List<String> firstDrain = storage.drainAll();
        assertEquals(List.of("id-1", "id-2", "id-3"), firstDrain);

        List<String> secondDrain = storage.drainAll();
        assertTrue(secondDrain.isEmpty());
    }

    @Test
    void drainAll_whenEmpty_shouldReturnEmptyList() {
        NumbersStorage storage = new NumbersStorage();

        List<String> drained = storage.drainAll();

        assertNotNull(drained);
        assertTrue(drained.isEmpty());
    }
}