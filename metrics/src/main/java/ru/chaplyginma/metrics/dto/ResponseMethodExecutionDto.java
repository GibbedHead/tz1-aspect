package ru.chaplyginma.metrics.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.chaplyginma.metrics.aggregateType.AggregateType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseMethodExecutionDto {
    private String className = "All";
    private String methodName = "All";
    private AggregateType aggregateType;
    private Double aggregateValue;
}
