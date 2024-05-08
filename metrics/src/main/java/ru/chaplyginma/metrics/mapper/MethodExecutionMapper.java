package ru.chaplyginma.metrics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;

@Mapper(componentModel = "spring")
public interface MethodExecutionMapper {
    @Mapping(target = "id", ignore = true)
    MethodExecution addDtoToMethodExecution(AddMethodExecutionDto addMethodExecutionDto);
}
