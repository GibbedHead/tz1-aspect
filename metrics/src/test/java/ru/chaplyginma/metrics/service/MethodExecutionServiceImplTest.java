package ru.chaplyginma.metrics.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.chaplyginma.metrics.aggregateType.AggregateType;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.dto.ResponseMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.repository.MethodExecutionRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MethodExecutionServiceImplTest {

    @Mock
    private MethodExecutionRepository methodExecutionRepository;

    @InjectMocks
    private MethodExecutionServiceImpl methodExecutionService;

    private AddMethodExecutionDto testAddMethodExecutionDto;
    private MethodExecution testMethodExecution;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        testAddMethodExecutionDto = new AddMethodExecutionDto(
                "SomeClass",
                "SomeMethod",
                100L,
                now
        );
        testMethodExecution = new MethodExecution(
                1L,
                "SomeClass",
                "SomeMethod",
                100L,
                now);
    }

    @Test
    void saveMethodExecution_ReturnsSavedMethodExecution() {
        given(methodExecutionRepository.save(any(MethodExecution.class)))
                .willReturn(testMethodExecution);

        MethodExecution savedMethodExecution = methodExecutionService.save(testAddMethodExecutionDto);

        assertThat(savedMethodExecution).isEqualTo(testMethodExecution);
        verify(methodExecutionRepository, times(1)).save(any(MethodExecution.class));
    }

    @Test
    void aggregateExecutionsByClassName_ReturnsResponseMethodExecutionDto() {

        String testClassName = "TestClassName";
        LocalDateTime testStartDate = LocalDateTime.now().minusDays(1);
        LocalDateTime testEndDate = LocalDateTime.now();
        AggregateType testAggregateType = AggregateType.AVERAGE;
        double testAggregateValue = 10.0;


        given(methodExecutionRepository.calculateAverageExecutionTimeByClassName(
                testClassName, testStartDate, testEndDate))
                .willReturn(testAggregateValue);


        ResponseMethodExecutionDto responseDto = methodExecutionService.aggregateExecutionsByClassName(
                testClassName, testStartDate, testEndDate, testAggregateType);


        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getClassName()).isEqualTo(testClassName);
        assertThat(responseDto.getAggregateType()).isEqualTo(testAggregateType);
        assertThat(responseDto.getAggregateValue()).isEqualTo(testAggregateValue);
    }

    @Test
    void aggregateExecutionsByClassNameAndMethodName_ReturnsResponseMethodExecutionDto() {

        String testClassName = "TestClassName";
        String testMethodName = "TestMethodName";
        LocalDateTime testStartDate = LocalDateTime.now().minusDays(1);
        LocalDateTime testEndDate = LocalDateTime.now();
        AggregateType testAggregateType = AggregateType.AVERAGE;
        double testAggregateValue = 10.0;


        given(methodExecutionRepository.calculateAverageExecutionTimeByClassNameAndMethodName(
                testClassName, testMethodName, testStartDate, testEndDate))
                .willReturn(testAggregateValue);


        ResponseMethodExecutionDto responseDto = methodExecutionService.aggregateExecutionsByClassNameAndMethodName(
                testClassName, testMethodName, testStartDate, testEndDate, testAggregateType);


        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getClassName()).isEqualTo(testClassName);
        assertThat(responseDto.getMethodName()).isEqualTo(testMethodName);
        assertThat(responseDto.getAggregateType()).isEqualTo(testAggregateType);
        assertThat(responseDto.getAggregateValue()).isEqualTo(testAggregateValue);
    }

    @Test
    void getAggregateValueByClassName_ReturnsCorrectAggregateValue() {

        String testClassName = "TestClassName";
        LocalDateTime testStartDate = LocalDateTime.now().minusDays(1);
        LocalDateTime testEndDate = LocalDateTime.now();
        AggregateType testAggregateType = AggregateType.AVERAGE;
        double testAggregateValue = 10.0;


        given(methodExecutionRepository.calculateAverageExecutionTimeByClassName(
                testClassName, testStartDate, testEndDate))
                .willReturn(testAggregateValue);


        double aggregateValue = methodExecutionService.getAggregateValueByClassName(
                testAggregateType, testClassName, testStartDate, testEndDate);


        assertThat(aggregateValue).isEqualTo(testAggregateValue);
    }

    @Test
    void getAggregateValueByClassNameAndMethodName_ReturnsCorrectAggregateValue() {

        String testClassName = "TestClassName";
        String testMethodName = "TestMethodName";
        LocalDateTime testStartDate = LocalDateTime.now().minusDays(1);
        LocalDateTime testEndDate = LocalDateTime.now();
        AggregateType testAggregateType = AggregateType.AVERAGE;
        double testAggregateValue = 10.0;


        given(methodExecutionRepository.calculateAverageExecutionTimeByClassNameAndMethodName(
                testClassName, testMethodName, testStartDate, testEndDate))
                .willReturn(testAggregateValue);


        double aggregateValue = methodExecutionService.getAggregateValueByClassNameAndMethodName(
                testAggregateType, testClassName, testMethodName, testStartDate, testEndDate);


        assertThat(aggregateValue).isEqualTo(testAggregateValue);
    }

}
