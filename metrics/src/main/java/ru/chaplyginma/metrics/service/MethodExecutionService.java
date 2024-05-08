package ru.chaplyginma.metrics.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.mapper.MethodExecutionMapper;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.repository.MethodExecutionRepository;

@Service
@RequiredArgsConstructor
public class MethodExecutionService {
    private static final Logger log = LoggerFactory.getLogger(MethodExecutionService.class);
    private final MethodExecutionRepository methodExecutionRepository;
    private final MethodExecutionMapper methodExecutionMapper = Mappers.getMapper(MethodExecutionMapper.class);

    public MethodExecution save(AddMethodExecutionDto addMethodExecutionDto) {
        MethodExecution savedMethodExecution = methodExecutionRepository.save(
                methodExecutionRepository.save(
                        methodExecutionMapper.addDtoToMethodExecution(addMethodExecutionDto)
                )
        );
        log.info("Added method execution: {}", savedMethodExecution);
        return savedMethodExecution;
    }
}
