package com.pet.stub_service.controller;

import com.pet.stub_service.dto.NumberRequest;
import com.pet.stub_service.service.NumberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/numbers")
public class NumberController {

    private final NumberService numberService;

    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping
    public String saveNumber(@RequestBody NumberRequest request) {
        numberService.save(request.id());
        return "Saved";
    }

    @GetMapping
    public String getNumbers() {
        return numberService.getAllAsString();
    }
}
