package com.example.pos.dto.request;

import com.example.pos.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusRequest {

    @NotNull(message = "Status is required")
    private TransactionStatus status;
}