package com.pet.stub_service.controller;

import com.pet.stub_service.component.NumbersStorage;
import com.pet.stub_service.dto.NumberRequest;
import com.pet.stub_service.service.NumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/numbers")
@Slf4j
public class NumberController {

    private final NumberService numberService;
    private final NumbersStorage numbersStorage;

    public NumberController(NumberService numberService, NumbersStorage numbersStorage) {
        this.numberService = numberService;
        this.numbersStorage = numbersStorage;
    }

    @PostMapping
    public String saveNumber(@RequestBody NumberRequest request) {
        log.info("Принято id = {} на сохранение", request.id());
        numberService.save(request.id());
        return "Saved";
    }

    @GetMapping
    public List<String> getAllFromKafka() {
        log.info("Запрос на получение данных");
        List<String> toReturn = numbersStorage.drainAll();
        log.info("К получению: {} ids", toReturn.size());
        return toReturn;
    }

}
