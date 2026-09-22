package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionStatus {
    @JsonProperty("pending") PENDING,
    @JsonProperty("paid") PAID,
    @JsonProperty("cancelled") CANCELLED
}