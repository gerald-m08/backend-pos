package com.task.pos.dto.response;

import com.task.pos.enums.PaymentMethod;
import com.task.pos.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TransactionHistoryResponse {

    private UUID id;
    private String transactionNumber;
    private UUID processedById;
    private LocalDateTime transactionDate;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private TransactionStatus status;
}