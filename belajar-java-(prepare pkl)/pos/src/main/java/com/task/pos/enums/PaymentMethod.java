package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethod {
    @JsonProperty("cash") CASH,
    @JsonProperty("transfer") TRANSFER,
    @JsonProperty("qris") QRIS,
    @JsonProperty("card") CARD
}