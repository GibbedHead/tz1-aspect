package ru.chaplyginma.metrics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chaplyginma.metrics.aggregateType.AggregateType;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.dto.ResponseMethodExecutionDto;
import ru.chaplyginma.metrics.mapper.MethodExecutionMapper;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.repository.MethodExecutionRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MethodExecutionService {
    private final MethodExecutionRepository methodExecutionRepository;
    private final MethodExecutionMapper methodExecutionMapper = Mappers.getMapper(MethodExecutionMapper.class);

    @Transactional
    public MethodExecution save(AddMethodExecutionDto addMethodExecutionDto) {
        MethodExecution savedMethodExecution = methodExecutionRepository.save(
                methodExecutionRepository.save(
                        methodExecutionMapper.addDtoToMethodExecution(addMethodExecutionDto)
                )
        );
        log.info("Added method execution: {}", savedMethodExecution);
        return savedMethodExecution;
    }

    @Transactional(readOnly = true)
    public ResponseMethodExecutionDto aggregateExecutionsByClassName(
            String className, LocalDateTime startDate, LocalDateTime endDate, AggregateType aggregateType
    ) {
        ResponseMethodExecutionDto responseMethodExecutionDto = new ResponseMethodExecutionDto();
        responseMethodExecutionDto.setClassName(className);
        responseMethodExecutionDto.setAggregateType(aggregateType);
        Double aggregateValue = switch (aggregateType) {
            case AVERAGE ->
                    methodExecutionRepository.calculateAverageExecutionTimeByClassName(className, startDate, endDate);
            case TOTAL ->
                    methodExecutionRepository.calculateTotalExecutionTimeByClassName(className, startDate, endDate);
            case MIN -> methodExecutionRepository.calculateMinExecutionTimeByClassName(className, startDate, endDate);
            case MAX -> methodExecutionRepository.calculateMaxExecutionTimeByClassName(className, startDate, endDate);
        };
        responseMethodExecutionDto.setAggregateValue(aggregateValue);
        return responseMethodExecutionDto;
    }

    @Transactional(readOnly = true)
    public ResponseMethodExecutionDto aggregateExecutionsByClassNameAndMethodName(
            String className, String methodName, LocalDateTime startDate, LocalDateTime endDate, AggregateType aggregateType
    ) {
        ResponseMethodExecutionDto responseMethodExecutionDto = new ResponseMethodExecutionDto();
        responseMethodExecutionDto.setClassName(className);
        responseMethodExecutionDto.setMethodName(methodName);
        responseMethodExecutionDto.setAggregateType(aggregateType);
        Double aggregateValue = switch (aggregateType) {
            case AVERAGE ->
                    methodExecutionRepository.calculateAverageExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);
            case TOTAL ->
                    methodExecutionRepository.calculateTotalExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);
            case MIN ->
                    methodExecutionRepository.calculateMinExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);
            case MAX ->
                    methodExecutionRepository.calculateMaxExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);
        };
        responseMethodExecutionDto.setAggregateValue(aggregateValue);
        return responseMethodExecutionDto;
    }
}
