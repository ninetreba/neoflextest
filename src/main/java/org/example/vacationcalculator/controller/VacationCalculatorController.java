package org.example.vacationcalculator.controller;

import jakarta.validation.Valid;
import org.example.vacationcalculator.dto.VacationRequest;
import org.example.vacationcalculator.service.VacationCalculatorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.math.BigDecimal;


@RestController
@RequestMapping("/calculate")
@Validated
public class VacationCalculatorController {

    private final VacationCalculatorService calculatorService;

    public VacationCalculatorController(VacationCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping
    public Map<String, Object> calculate(@Valid @ModelAttribute VacationRequest request) {
        BigDecimal result = calculatorService.calculate(request);
        return Map.of("vacationPay", result);
    }
}