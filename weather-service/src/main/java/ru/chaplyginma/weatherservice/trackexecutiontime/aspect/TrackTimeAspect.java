package ru.chaplyginma.weatherservice.trackexecutiontime.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TrackTimeAspect {
    @Pointcut("@annotation(ru.chaplyginma.weatherservice.trackexecutiontime.annotation.TrackTime)")
    public void trackTimeAnnotationPointcut() {
    }

    @Around("trackTimeAnnotationPointcut()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;
            log.info("\tClass '{}', method '{}', time '{}'", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), executionTime);

        } catch (Throwable e) {
            log.error("Ошибка '{}' при выполнении метода '{}'", e.getMessage(), joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
            throw e;
        }

        return result;
    }
}
