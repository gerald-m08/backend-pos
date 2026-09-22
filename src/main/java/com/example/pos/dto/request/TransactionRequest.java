package com.example.pos.dto.request;

import com.example.pos.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TransactionRequest {

    @NotEmpty(message = "Transaction items are required")
    @Valid
    private List<TransactionItemRequest> items;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private BigDecimal paymentAmount;
}