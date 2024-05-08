package ru.chaplyginma.weatherservice.trackexecutiontime.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.chaplyginma.weatherservice.trackexecutiontime.metricsclient.MetricsClient;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackAsyncTimeAspect {
    private final MetricsClient metricsClient;


    @Pointcut("@annotation(ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackAsyncTime)")
    public void trackAsyncTimeAnnotationPointcut() {
    }

    @Around("trackAsyncTimeAnnotationPointcut()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed();

            if (result instanceof CompletableFuture<?> futureResult) {
                futureResult.whenComplete((res, ex) -> {
                    long executionTime = System.currentTimeMillis() - startTime;
                    metricsClient.logExecutionTime(joinPoint, executionTime);
                });
            } else {
                long executionTime = System.currentTimeMillis() - startTime;
                metricsClient.logExecutionTime(joinPoint, executionTime);
            }

        } catch (Throwable e) {
            log.error("Error '{}' executing method '{}'", e.getMessage(), joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
            throw e;
        }

        return result;
    }

}
