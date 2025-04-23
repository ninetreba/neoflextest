package org.example.vacationcalculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class VacationRequest {

    @NotNull(message = "Average salary is required")
    @Min(value = 1, message = "Average salary must be greater than 0")
    private BigDecimal averageSalary;

    @Min(value = 1, message = "Vacation days must be greater than 0")
    private Integer vacationDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}