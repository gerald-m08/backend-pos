package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StockHistoryType {
    @JsonProperty("in") IN,
    @JsonProperty("out") OUT,
    @JsonProperty("adjustment") ADJUSTMENT,
    @JsonProperty("return") RETURN
}