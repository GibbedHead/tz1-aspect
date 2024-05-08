package ru.chaplyginma.metrics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.service.MethodExecutionService;

@RestController
@RequestMapping(path = "/v1/metrics")
@RequiredArgsConstructor
@Slf4j
public class MethodExecutionController {
    private final MethodExecutionService methodExecutionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MethodExecution save(@Valid @RequestBody AddMethodExecutionDto addMethodExecutionDto) {
        log.info("Request save method execution: {}", addMethodExecutionDto);
        return methodExecutionService.save(addMethodExecutionDto);
    }
}
