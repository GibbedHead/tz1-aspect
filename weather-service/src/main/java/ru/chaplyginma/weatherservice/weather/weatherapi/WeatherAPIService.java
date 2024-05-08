package ru.chaplyginma.weatherservice.weather.weatherapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherAPIService {
    private static final String API_URL = "http://api.weatherapi.com/v1/current.json";
    private static final String API_KEY = "6fb756f62b964c8181c211013240705";

    public String getCityTemperature(String city) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .queryParam("key", API_KEY)
                .queryParam("q", city)
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String jsonResponse = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.path("current").path("temp_c").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Async
    public CompletableFuture<String> asyncGetCityTemperature(String city) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .queryParam("key", API_KEY)
                .queryParam("q", city)
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String jsonResponse = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return CompletableFuture.completedFuture(jsonNode.path("current").path("temp_c").toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
