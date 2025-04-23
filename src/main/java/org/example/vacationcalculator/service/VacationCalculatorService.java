package org.example.vacationcalculator.service;

import org.example.vacationcalculator.dto.VacationRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

@Service
public class VacationCalculatorService {

    private final HolidayUtils holidayUtils;
    private final HolidayClient holidayClient;

    public VacationCalculatorService(HolidayUtils holidayUtils, HolidayClient holidayClient) {
        this.holidayUtils = holidayUtils;
        this.holidayClient = holidayClient;
    }

    public BigDecimal calculate(VacationRequest request) {
        if (request.getAverageSalary() == null || request.getAverageSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Average salary must be greater than 0");
        }

        if (request.getVacationDays() != null) {
            if (request.getVacationDays() <= 0) {
                throw new IllegalArgumentException("Vacation days must be greater than 0");
            }
            return calculateVacationPay(request.getAverageSalary(), request.getVacationDays());
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new IllegalArgumentException("End date must not be before start date");
            }
            return calculateVacationPayWithDates(request.getAverageSalary(), request.getStartDate(), request.getEndDate());
        }
        throw new IllegalArgumentException("Provide either vacationDays or startDate and endDate");
    }

    public BigDecimal calculateVacationPay(BigDecimal averageSalary, int vacationDays) {
        BigDecimal averageDailySalary = averageSalary.divide(new BigDecimal("29.3"), RoundingMode.HALF_UP);
        return averageDailySalary.multiply(new BigDecimal(vacationDays));
    }

    public BigDecimal calculateVacationPayWithDates(BigDecimal averageSalary, LocalDate start, LocalDate end) {
        Set<LocalDate> holidays = holidayClient.fetchHolidays();
        int workingDays = holidayUtils.getWorkingDaysBetween(start, end, holidays);
        return calculateVacationPay(averageSalary, workingDays);
    }
}
