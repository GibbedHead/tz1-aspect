package ru.chaplyginma.weatherservice.weather.visualcrossing;

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
public class VisualcrossingService {
    private static final String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String API_KEY = "B64BZHVMD5Q9HVD6L748HYU67";

    public String getCityTemperature(String city) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(city)
                .queryParam("key", API_KEY)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "current")
                .queryParam("contentType", "json")
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String jsonResponse = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.path("days").path(0).path("temp").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Async
    public CompletableFuture<String> asyncGetCityTemperature(String city) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(city)
                .queryParam("key", API_KEY)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "current")
                .queryParam("contentType", "json")
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String jsonResponse = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return CompletableFuture.completedFuture(jsonNode.path("days").path(0).path("temp").toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
