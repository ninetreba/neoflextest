package org.example.vacationcalculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Component
public class HolidayClient {

    @Value("${calendarific.api.url}")
    private String apiUrl;

    @Value("${calendarific.api.key}")
    private String apiKey;

    @Value("${calendarific.country}")
    private String country;

    @Value("${calendarific.year}")
    private String year;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Set<LocalDate> fetchHolidays() {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("api_key", apiKey)
                .queryParam("country", country)
                .queryParam("year", year)
                .build()
                .toUriString();

        String json = restTemplate.getForObject(url, String.class);
        Set<LocalDate> holidays = new HashSet<>();

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode holidaysNode = root.path("response").path("holidays");

            for (JsonNode holiday : holidaysNode) {
                String dateIso = holiday.path("date").path("iso").asText();
                holidays.add(LocalDate.parse(dateIso));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse holiday API response", e);
        }

        return holidays;
    }
}