package ru.chaplyginma.weatherservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.chaplyginma.weatherservice.weather.WeatherService;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAsync
public class WeatherServiceApplication {
    private final WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void print() {
        weatherService.asyncPrintForCity("Moscow");
        weatherService.printForCity("Moscow");
        weatherService.asyncPrintForCity("Rostov-on-Don");
        weatherService.printForCity("Rostov-on-Don");
    }

}
