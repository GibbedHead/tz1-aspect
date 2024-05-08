package ru.chaplyginma.weatherservice.trackexecutiontime.metricsclient;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.chaplyginma.weatherservice.trackexecutiontime.dto.AddMethodExecutionDto;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MetricsClient {
    @Value("${app.tracktime.api-url}")
    private String tracktimeAPIUrl;

    public void logExecutionTime(ProceedingJoinPoint joinPoint, long executionTime) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        AddMethodExecutionDto addMethodExecutionDto = new AddMethodExecutionDto(
                className,
                methodName,
                executionTime,
                LocalDateTime.now()
        );
        WebClient webClient = WebClient.create(tracktimeAPIUrl);
        webClient
                .post()
                .uri("/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(addMethodExecutionDto))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> log.info("Metrics for method '{}' saved successfully", methodName),
                        error -> log.error(
                                "Metrics for method '{}' save failed. Error: {}",
                                methodName,
                                error.getMessage())
                );
    }
}
