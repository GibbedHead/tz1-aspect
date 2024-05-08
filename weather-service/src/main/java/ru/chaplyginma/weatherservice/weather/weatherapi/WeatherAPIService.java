package ru.chaplyginma.weatherservice.weather.weatherapi;

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
public class WeatherAPIService {
    private static final String API_URL = "http://api.weatherapi.com/v1/current.json";
    private static final String API_KEY = "6fb756f62b964c8181c211013240705";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public WeatherAPIService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
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
                .queryParam("key", API_KEY)
                .queryParam("q", city)
                .build()
                .toUri();
    }

    private String parseTemperatureFromJson(String jsonResponse) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.path("current").path("temp_c").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
