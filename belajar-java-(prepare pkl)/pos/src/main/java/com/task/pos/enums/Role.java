package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("admin") ADMIN,
    @JsonProperty("cashier") CASHIER
}