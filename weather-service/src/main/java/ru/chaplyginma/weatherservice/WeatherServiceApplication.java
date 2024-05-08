package ru.chaplyginma.weatherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.chaplyginma.weatherservice.weather.visualcrossing.VisualcrossingService;
import ru.chaplyginma.weatherservice.weather.weatherapi.WeatherAPIService;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class WeatherServiceApplication {
    private final WeatherAPIService weatherAPIService;
    private final VisualcrossingService visualcrossingService;

    @Autowired
    public WeatherServiceApplication(WeatherAPIService weatherAPIService, VisualcrossingService visualcrossingService) {
        this.weatherAPIService = weatherAPIService;
        this.visualcrossingService = visualcrossingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void print() {
        asyncPrintForCity("Moscow");
        printForCity("Moscow");
        asyncPrintForCity("Rostov-on-Don");
        printForCity("Rostov-on-Don");
        System.exit(0);
    }

    public void printForCity(String city) {
        System.out.printf("Sync weatherAPI. %s: %s℃%n", city, weatherAPIService.getCityTemperature(city));
        System.out.printf("Sync visualcrossing. %s: %s℃%n", city, visualcrossingService.getCityTemperature(city));
    }

    public void asyncPrintForCity(String city) {
        CompletableFuture<String> weatherAPIFutureResult = weatherAPIService.asyncGetCityTemperature(city);
        weatherAPIFutureResult.thenAccept(result -> System.out.printf("Async weatherAPI. %s: %s℃%n", city, result));

        CompletableFuture<String> visualcrossingFutureResult = visualcrossingService.asyncGetCityTemperature(city);
        visualcrossingFutureResult.thenAccept(result -> System.out.printf("Async visualcrossing. %s: %s℃%n", city, result));
    }

}
