package ru.chaplyginma.weatherservice.trackexecutiontime.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMethodExecutionDto {

    String className;

    String methodName;

    Long executionTime;

    LocalDateTime executionTimestamp;
}
