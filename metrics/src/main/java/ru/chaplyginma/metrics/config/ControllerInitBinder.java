package ru.chaplyginma.metrics.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import ru.chaplyginma.metrics.aggregateType.AggregateType;

@ControllerAdvice
public class ControllerInitBinder {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AggregateType.class, new AggregateTypeEditor());
    }
}
