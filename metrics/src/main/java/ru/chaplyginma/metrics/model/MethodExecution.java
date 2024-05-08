package ru.chaplyginma.metrics.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MethodExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "method_execution_id_seq", allocationSize = 1)
    Long id;

    String className;

    String methodName;

    Long executionTime;

    LocalDateTime executionTimestamp;
}
