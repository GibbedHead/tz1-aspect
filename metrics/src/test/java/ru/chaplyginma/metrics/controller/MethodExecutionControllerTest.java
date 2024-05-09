package ru.chaplyginma.metrics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.chaplyginma.metrics.aggregateType.AggregateType;
import ru.chaplyginma.metrics.dto.AddMethodExecutionDto;
import ru.chaplyginma.metrics.dto.ResponseMethodExecutionDto;
import ru.chaplyginma.metrics.model.MethodExecution;
import ru.chaplyginma.metrics.service.MethodExecutionService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MethodExecutionController.class)
class MethodExecutionControllerTest {

    private static final String API_URL = "/v1/metrics";
    private static final String CLASS_URL = API_URL + "/class/{className}";
    private static final String CLASS_METHOD_URL = API_URL + "/class/{className}/method/{methodName}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MethodExecutionService methodExecutionService;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final AddMethodExecutionDto addMethodExecutionDto = new AddMethodExecutionDto();
    private static final MethodExecution methodExecution = new MethodExecution();
    private static final ResponseMethodExecutionDto responseMethodExecutionDto = new ResponseMethodExecutionDto();
    private static final AddMethodExecutionDto invalidAddMethodExecutionDto = new AddMethodExecutionDto();

    @BeforeAll
    static void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        addMethodExecutionDto.setClassName("SomeClass");
        addMethodExecutionDto.setMethodName("SomeMethod");
        addMethodExecutionDto.setExecutionTime(100L);
        addMethodExecutionDto.setExecutionTimestamp(LocalDateTime.now());

        methodExecution.setId(1L);
        methodExecution.setClassName("SomeClass");
        methodExecution.setMethodName("SomeMethod");
        methodExecution.setExecutionTime(100L);
        methodExecution.setExecutionTimestamp(LocalDateTime.now());

        responseMethodExecutionDto.setClassName("SomeClass");
        responseMethodExecutionDto.setMethodName("SomeMethod");
        responseMethodExecutionDto.setAggregateType(AggregateType.AVERAGE);
        responseMethodExecutionDto.setAggregateValue(50.0);
    }

    @Test
    void save_ValidInput() throws Exception {
        String validDtoJson = objectMapper.writeValueAsString(addMethodExecutionDto);

        given(methodExecutionService.save(any(AddMethodExecutionDto.class)))
                .willReturn(methodExecution);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validDtoJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.className").value("SomeClass"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.methodName").value("SomeMethod"));
    }

    @Test
    void save_InvalidInput() throws Exception {
        String invalidDtoJson = objectMapper.writeValueAsString(invalidAddMethodExecutionDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getExecutionsByClassNameAndMethodName_ValidInput() throws Exception {
        String className = "SomeClass";
        String methodName = "SomeMethod";
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        AggregateType aggregateType = AggregateType.AVERAGE;

        given(
                methodExecutionService.aggregateExecutionsByClassNameAndMethodName(
                        className, methodName, startDate, endDate, aggregateType
                )
        )
                .willReturn(responseMethodExecutionDto);

        mockMvc.perform(MockMvcRequestBuilders.get(CLASS_METHOD_URL, className, methodName)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("aggregateType", aggregateType.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.className").value(className))
                .andExpect(MockMvcResultMatchers.jsonPath("$.methodName").value(methodName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aggregateValue").value(50.0));
    }

    @Test
    void getExecutionsByClassName_ValidInput() throws Exception {
        String className = "SomeClass";
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        AggregateType aggregateType = AggregateType.AVERAGE;

        given(methodExecutionService.aggregateExecutionsByClassName(className, startDate, endDate, aggregateType))
                .willReturn(responseMethodExecutionDto);

        mockMvc.perform(MockMvcRequestBuilders.get(CLASS_URL, className)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("aggregateType", aggregateType.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.className").value(className))
                .andExpect(MockMvcResultMatchers.jsonPath("$.methodName").value("SomeMethod"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aggregateValue").value(50.0));
    }
}

