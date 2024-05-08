package ru.chaplyginma.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chaplyginma.metrics.model.MethodExecution;

public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {
}
