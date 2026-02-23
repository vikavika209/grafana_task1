package com.pet.stub_service.service;

import com.pet.stub_service.kafka.KafkaBatchProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NumberServiceTest {

    private KafkaBatchProducer producer;
    private NumberService service;

    @BeforeEach
    void setUp() {
        producer = mock(KafkaBatchProducer.class);
        service = new NumberService(producer);
    }

    @Test
    void shouldNotSendToKafkaUntil100Accumulated() {

        for (int i = 1; i <= 99; i++) {
            service.save("id-" + i);
        }

        verify(producer, never()).sendMany(anyList());

        service.save("id-100");
        verify(producer, times(1)).sendMany(anyList());
    }

    @Test
    void when100OrMore_thenSendBatchAndClearQueue() throws Exception {
        for (int i = 1; i <= 100; i++) {
            service.save("id-" + i);
        }

        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(producer, times(1)).sendMany(captor.capture());

        List<String> sent = captor.getValue();
        assertThat(sent).hasSize(100);
        assertThat(sent).contains("id-1", "id-50", "id-100");

        Queue<String> queue = extractQueue(service);
        assertThat(queue).isEmpty();

        for (int i = 101; i <= 199; i++) {
            service.save("id-" + i);
        }
        verify(producer, times(1)).sendMany(anyList());

        service.save("id-200");

        verify(producer, times(2)).sendMany(anyList());
    }

    @SuppressWarnings("unchecked")
    private static Queue<String> extractQueue(NumberService service) throws Exception {
        Field field = NumberService.class.getDeclaredField("numbers");
        field.setAccessible(true);
        return (Queue<String>) field.get(service);
    }
}
