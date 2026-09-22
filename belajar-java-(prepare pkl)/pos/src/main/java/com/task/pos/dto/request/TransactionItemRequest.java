package com.task.pos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionItemRequest {

    @NotNull
    private UUID productId;

    @NotNull
    @Positive
    private Integer quantity;
}