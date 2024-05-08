package ru.chaplyginma.metrics.config;

import ru.chaplyginma.metrics.aggregateType.AggregateType;

import java.beans.PropertyEditorSupport;

public class AggregateTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(AggregateType.valueOf(text.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for AggregateType: " + text);
        }
    }
}
