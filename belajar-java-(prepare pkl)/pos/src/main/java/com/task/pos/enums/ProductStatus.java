package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductStatus {
    @JsonProperty("active") ACTIVE,
    @JsonProperty("inactive") INACTIVE
}