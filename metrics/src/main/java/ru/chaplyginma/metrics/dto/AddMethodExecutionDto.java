package ru.chaplyginma.metrics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMethodExecutionDto {
    @NotBlank
    @Size(max = 255)
    String className;
    @NotBlank
    @Size(max = 255)
    String methodName;
    @NotNull
    @PositiveOrZero
    Long executionTime;
    @NotNull
    LocalDateTime executionTimestamp;
}
