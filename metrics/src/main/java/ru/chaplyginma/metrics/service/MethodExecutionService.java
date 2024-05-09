package ru.chaplyginma.metrics.service;

import ru.chaplyginma.metrics.aggregateType.AggregateType;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.dto.ResponseMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;

import java.time.LocalDateTime;

public interface MethodExecutionService {
    MethodExecution save(AddMethodExecutionDto addMethodExecutionDto);

    ResponseMethodExecutionDto aggregateExecutionsByClassName(
            String className, LocalDateTime startDate, LocalDateTime endDate, AggregateType aggregateType
    );

    ResponseMethodExecutionDto aggregateExecutionsByClassNameAndMethodName(
            String className, String methodName, LocalDateTime startDate, LocalDateTime endDate, AggregateType aggregateType
    );
}
