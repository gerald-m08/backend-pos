package com.task.pos.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.pos.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotEmpty
    @Valid
    private List<TransactionItemRequest> items;

    @NotNull
    private PaymentMethod paymentMethod;

    @Positive(message = "Payment amount must be greater than 0")
    private BigDecimal paymentAmount;


}