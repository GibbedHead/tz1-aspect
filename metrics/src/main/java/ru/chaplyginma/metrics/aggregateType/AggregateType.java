package ru.chaplyginma.metrics.aggregateType;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AggregateType")
public enum AggregateType {
    @JsonProperty("total")
    TOTAL,
    @JsonProperty("average")
    AVERAGE,
    @JsonProperty("max")
    MAX,
    @JsonProperty("min")
    MIN
}
