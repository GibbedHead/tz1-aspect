package ru.chaplyginma.weatherservice.weather.visualcrossing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackAsyncTime;
import ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackTime;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class VisualcrossingService {
    private static final String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String API_KEY = "B64BZHVMD5Q9HVD6L748HYU67";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public VisualcrossingService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
        this.objectMapper = objectMapper;
    }

    @TrackTime
    public String getCityTemperature(String city) {
        URI uri = buildUri(city);
        String jsonResponse = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return parseTemperatureFromJson(jsonResponse);
    }

    @TrackAsyncTime
    @Async
    public CompletableFuture<String> asyncGetCityTemperature(String city) {
        URI uri = buildUri(city);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseTemperatureFromJson)
                .toFuture();
    }

    private URI buildUri(String city) {
        return UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(city)
                .queryParam("key", API_KEY)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "current")
                .queryParam("contentType", "json")
                .build()
                .toUri();
    }

    private String parseTemperatureFromJson(String jsonResponse) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.path("days").path(0).path("temp").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
