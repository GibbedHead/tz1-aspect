package ru.chaplyginma.metrics.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.chaplyginma.metrics.model.MethodExecution;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class MethodExecutionRepositoryIntegrationTests {
    @Autowired
    private MethodExecutionRepository methodExecutionRepository;

    @Test
    void testSave_GivenValidMethodExecution_WhenSaving_ThenEntityIsPersisted() {
        MethodExecution validMethodExecution = getMethodExecution1();
        MethodExecution savedMethodExecution = methodExecutionRepository.save(validMethodExecution);

        assertThat(savedMethodExecution).extracting(MethodExecution::getMethodName).isEqualTo(validMethodExecution.getMethodName());
    }

    @Test
    public void testCalculateAverageExecutionTimeByClassName() {

        String className = "SomeClass";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());

        Double averageExecutionTime = methodExecutionRepository.calculateAverageExecutionTimeByClassName(className, startDate, endDate);


        assertThat(averageExecutionTime).isEqualTo(75.0);

    }

    @Test
    public void testCalculateTotalExecutionTimeByClassName() {

        String className = "SomeClass";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());


        Double totalExecutionTime = methodExecutionRepository.calculateTotalExecutionTimeByClassName(className, startDate, endDate);


        assertThat(totalExecutionTime).isEqualTo(150.0);

    }

    @Test
    public void testCalculateMinExecutionTimeByClassName() {

        String className = "SomeClass";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());


        Double minExecutionTime = methodExecutionRepository.calculateMinExecutionTimeByClassName(className, startDate, endDate);


        assertThat(minExecutionTime).isEqualTo(50.0);

    }

    @Test
    public void testCalculateMaxExecutionTimeByClassName() {

        String className = "SomeClass";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());


        Double maxExecutionTime = methodExecutionRepository.calculateMaxExecutionTimeByClassName(className, startDate, endDate);


        assertThat(maxExecutionTime).isEqualTo(100.0);

    }

    @Test
    public void testCalculateAverageExecutionTimeByClassNameAndMethodName() {

        String className = "SomeClass";
        String methodName = "SomeMethod1";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());

        Double averageExecutionTime = methodExecutionRepository.calculateAverageExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);


        assertThat(averageExecutionTime).isEqualTo(75.0);

    }

    @Test
    public void testCalculateTotalExecutionTimeByClassNameAndMethodName() {

        String className = "SomeClass";
        String methodName = "SomeMethod1";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());

        Double totalExecutionTime = methodExecutionRepository.calculateTotalExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);


        assertThat(totalExecutionTime).isEqualTo(150.0);

    }

    @Test
    public void testCalculateMinExecutionTimeByClassNameAndMethodName() {

        String className = "SomeClass";
        String methodName = "SomeMethod1";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());

        Double minExecutionTime = methodExecutionRepository.calculateMinExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);


        assertThat(minExecutionTime).isEqualTo(50.0);

    }

    @Test
    public void testCalculateMaxExecutionTimeByClassNameAndMethodName() {

        String className = "SomeClass";
        String methodName = "SomeMethod1";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        MethodExecution m1 = methodExecutionRepository.save(getMethodExecution1());
        MethodExecution m2 = methodExecutionRepository.save(getMethodExecution11());

        Double maxExecutionTime = methodExecutionRepository.calculateMaxExecutionTimeByClassNameAndMethodName(className, methodName, startDate, endDate);


        assertThat(maxExecutionTime).isEqualTo(100.0);

    }


    private MethodExecution getMethodExecution1() {
        return new MethodExecution(
                null,
                "SomeClass",
                "SomeMethod1",
                100L,
                LocalDateTime.now()
        );
    }

    private MethodExecution getMethodExecution11() {
        return new MethodExecution(
                null,
                "SomeClass",
                "SomeMethod1",
                50L,
                LocalDateTime.now()
        );
    }
}
