package ru.chaplyginma.weatherservice.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackAsyncTime;
import ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackTime;
import ru.chaplyginma.weatherservice.weather.visualcrossing.VisualcrossingService;
import ru.chaplyginma.weatherservice.weather.weatherapi.WeatherAPIService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherAPIService weatherAPIService;
    private final VisualcrossingService visualcrossingService;

    @TrackTime
    public void printForCity(String city) {
        log.info("Sync weatherAPI. {}: {} ℃", city, weatherAPIService.getCityTemperature(city));
        log.info("Sync visualcrossing. {}: {} ℃", city, visualcrossingService.getCityTemperature(city));
    }

    @TrackAsyncTime
    @Async
    public void asyncPrintForCity(String city) {
        CompletableFuture<String> weatherAPIFutureResult = weatherAPIService.asyncGetCityTemperature(city);
        weatherAPIFutureResult.thenAccept(result -> log.info("Async weatherAPI. {}: {} ℃", city, result));

        CompletableFuture<String> visualcrossingFutureResult = visualcrossingService.asyncGetCityTemperature(city);
        visualcrossingFutureResult.thenAccept(result -> log.info("Async visualcrossing. {}: {} ℃", city, result));
    }
}
