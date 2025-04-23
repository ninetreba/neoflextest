package org.example.vacationcalculator.service;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
public class HolidayUtils {

    public int getWorkingDaysBetween(LocalDate start, LocalDate end, Set<LocalDate> holidays) {
        int count = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (isWorkingDay(date, holidays)) {
                count++;
            }
        }
        return count;
    }

    public boolean isWorkingDay(LocalDate date, Set<LocalDate> holidays) {
        return !(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                holidays.contains(date));
    }
}