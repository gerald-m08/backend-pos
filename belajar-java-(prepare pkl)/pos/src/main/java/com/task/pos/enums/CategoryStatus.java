package com.task.pos.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CategoryStatus {
    @JsonProperty("active") ACTIVE,
    @JsonProperty("inctive") INACTIVE
}