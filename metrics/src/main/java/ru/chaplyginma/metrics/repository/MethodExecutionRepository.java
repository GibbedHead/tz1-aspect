package ru.chaplyginma.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.chaplyginma.metrics.model.MethodExecution;

import java.time.LocalDateTime;

public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {

    @Query("SELECT AVG(m.executionTime) FROM MethodExecution m WHERE m.className = :className " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateAverageExecutionTimeByClassName(
            @Param("className") String className,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(m.executionTime) FROM MethodExecution m WHERE m.className = :className " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateTotalExecutionTimeByClassName(
            @Param("className") String className,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT MIN(m.executionTime) FROM MethodExecution m WHERE m.className = :className " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateMinExecutionTimeByClassName(
            @Param("className") String className,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT MAX(m.executionTime) FROM MethodExecution m WHERE m.className = :className " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateMaxExecutionTimeByClassName(
            @Param("className") String className,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT AVG(m.executionTime) FROM MethodExecution m WHERE m.className = :className AND m.methodName = :methodName " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateAverageExecutionTimeByClassNameAndMethodName(
            @Param("className") String className,
            @Param("methodName") String methodName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(m.executionTime) FROM MethodExecution m WHERE m.className = :className AND m.methodName = :methodName " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateTotalExecutionTimeByClassNameAndMethodName(
            @Param("className") String className,
            @Param("methodName") String methodName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT MIN(m.executionTime) FROM MethodExecution m WHERE m.className = :className AND m.methodName = :methodName " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateMinExecutionTimeByClassNameAndMethodName(
            @Param("className") String className,
            @Param("methodName") String methodName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT MAX(m.executionTime) FROM MethodExecution m WHERE m.className = :className AND m.methodName = :methodName " +
            "AND (COALESCE(:startDate, m.executionTimestamp) <= m.executionTimestamp) " +
            "AND (COALESCE(:endDate, m.executionTimestamp) >= m.executionTimestamp)")
    Double calculateMaxExecutionTimeByClassNameAndMethodName(
            @Param("className") String className,
            @Param("methodName") String methodName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
