package ru.chaplyginma.weatherservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.chaplyginma.weatherservice.weather.WeatherService;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAsync
public class WeatherServiceApplication implements CommandLineRunner {
    private final WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        weatherService.asyncPrintForCity("Moscow");
        weatherService.printForCity("Moscow");
        weatherService.asyncPrintForCity("Rostov-on-Don");
        weatherService.printForCity("Rostov-on-Don");
    }

}
