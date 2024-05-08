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
        log.info("Sync weatherAPI. {}: {} C", city, weatherAPIService.getCityTemperature(city));
        log.info("Sync visualcrossing. {}: {} C", city, visualcrossingService.getCityTemperature(city));
    }

    @TrackAsyncTime
    @Async
    public CompletableFuture<Void> asyncPrintForCity(String city) {
        CompletableFuture<String> weatherAPIFutureResult = weatherAPIService.asyncGetCityTemperature(city);
        CompletableFuture<String> visualcrossingFutureResult = visualcrossingService.asyncGetCityTemperature(city);

        return CompletableFuture.allOf(weatherAPIFutureResult, visualcrossingFutureResult)
                .thenAcceptAsync(ignored ->
                        log.info("Async weatherAPI. {}: {} C", city, weatherAPIFutureResult.join())
                ).thenAcceptAsync(ignored ->
                        log.info("Async visualcrossing. {}: {} C", city, visualcrossingFutureResult.join())
                );
    }
}
