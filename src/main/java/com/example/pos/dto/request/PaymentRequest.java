package com.example.pos.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Transaction ID is required")
    private UUID transactionId;
}