package ru.chaplyginma.metrics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.chaplyginma.metrics.aggregateType.AggregateType;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.dto.ResponseMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.service.MethodExecutionService;

import java.time.LocalDateTime;

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

    @GetMapping("/class/{className}/method/{methodName}")
    @Operation(summary = "Get aggregate execution data by class and method name",
            description = "Get aggregate execution data (average, min, max) for a specified method of a specified class within a given time range",
            responses = {
                    @ApiResponse(
                            description = "Aggregate execution data retrieved successfully",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseMethodExecutionDto.class))
                    ),
                    @ApiResponse(description = "Invalid request or data not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            })
    public ResponseMethodExecutionDto getExecutionsByClassNameAndMethodName(
            @Parameter(description = "Name of the class") @PathVariable String className,
            @Parameter(description = "Name of the method") @PathVariable String methodName,
            @Parameter(description = "Start date for filtering (inclusive)", schema = @Schema(implementation = LocalDateTime.class)) @RequestParam(required = false) LocalDateTime startDate,
            @Parameter(description = "End date for filtering (inclusive)", schema = @Schema(implementation = LocalDateTime.class)) @RequestParam(required = false) LocalDateTime endDate,
            @Parameter(description = "Type of aggregation (default = average)", schema = @Schema(implementation = AggregateType.class)) @RequestParam(defaultValue = "average") AggregateType aggregateType) {
        log.info("Request get method execution aggregation for class '{}', method '{}, startDate '{}', endDate '{}', aggregateType '{}'", className, methodName, startDate, endDate, aggregateType);
        return methodExecutionService.aggregateExecutionsByClassNameAndMethodName(className, methodName, startDate, endDate, aggregateType);
    }
}
