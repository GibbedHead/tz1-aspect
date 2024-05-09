package ru.chaplyginma.metrics.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddMethodExecutionDtoTest {
    private final Validator validator;

    public AddMethodExecutionDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEmptyClassName() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("");
        dto.setMethodName("someMethod");
        dto.setExecutionTime(100L);
        dto.setExecutionTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void testClassNameLength() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("a".repeat(256));
        dto.setMethodName("someMethod");
        dto.setExecutionTime(100L);
        dto.setExecutionTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void testEmptyMethodName() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("SomeClass");
        dto.setMethodName("");
        dto.setExecutionTime(100L);
        dto.setExecutionTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void testMethodNameLength() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("SomeClass");
        dto.setMethodName("a".repeat(256));
        dto.setExecutionTime(100L);
        dto.setExecutionTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void testNegativeExecutionTime() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("SomeClass");
        dto.setMethodName("someMethod");
        dto.setExecutionTime(-100L);
        dto.setExecutionTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void testNullExecutionTimestamp() {
        AddMethodExecutionDto dto = new AddMethodExecutionDto();
        dto.setClassName("SomeClass");
        dto.setMethodName("someMethod");
        dto.setExecutionTime(100L);
        dto.setExecutionTimestamp(null);

        Set<ConstraintViolation<AddMethodExecutionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }
}
