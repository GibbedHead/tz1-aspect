package ru.chaplyginma.metrics.aggregateType;

import org.junit.jupiter.api.Test;
import ru.chaplyginma.metrics.config.AggregateTypeEditor;

import static org.assertj.core.api.Assertions.assertThat;

public class AggregateTypeTest {

    @Test
    public void testAggregateTypeConversion() {

        AggregateTypeEditor editor = new AggregateTypeEditor();

        editor.setAsText("total");

        assertThat(editor.getValue()).isEqualTo(AggregateType.TOTAL);
    }
}
