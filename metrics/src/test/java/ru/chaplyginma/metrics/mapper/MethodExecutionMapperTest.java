package ru.chaplyginma.metrics.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodExecutionMapperTest {
    private final MethodExecutionMapper mapper = Mappers.getMapper(MethodExecutionMapper.class);

    @Test
    public void testAddDtoToMethodExecution() {

        AddMethodExecutionDto addMethodExecutionDto = new AddMethodExecutionDto();
        addMethodExecutionDto.setClassName("TestClass");
        addMethodExecutionDto.setMethodName("testMethod");
        addMethodExecutionDto.setExecutionTime(100L);
        addMethodExecutionDto.setExecutionTimestamp(LocalDateTime.now());

        MethodExecution methodExecution = mapper.addDtoToMethodExecution(addMethodExecutionDto);

        assertThat(methodExecution).isNotNull();
        assertThat(methodExecution.getClassName()).isEqualTo(addMethodExecutionDto.getClassName());
        assertThat(methodExecution.getMethodName()).isEqualTo(addMethodExecutionDto.getMethodName());
        assertThat(methodExecution.getExecutionTime()).isEqualTo(addMethodExecutionDto.getExecutionTime());
        assertThat(methodExecution.getExecutionTimestamp()).isEqualTo(addMethodExecutionDto.getExecutionTimestamp());
    }

    @Test
    public void testNullToMethodExecution() {
        MethodExecution methodExecution = mapper.addDtoToMethodExecution(null);

        assertThat(methodExecution).isNull();
    }
}
