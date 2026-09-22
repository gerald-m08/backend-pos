package com.task.pos.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.pos.enums.PaymentMethod;
import com.task.pos.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class TransactionResponse {

    private UUID id;
    private String transactionNumber;
    private UUID processedById;
    private LocalDateTime transactionDate;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private BigDecimal paymentAmount;
    private BigDecimal changeAmount;
    private TransactionStatus status;
    private List<TransactionItemResponse> items;
}