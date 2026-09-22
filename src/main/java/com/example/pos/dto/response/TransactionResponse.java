package com.example.pos.dto.response;

import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;
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

    private UUID cashierId;

    private String cashierUsername;

    private LocalDateTime transactionDate;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private String paymentReference;

    private TransactionStatus status;

    private BigDecimal paymentAmount;

    private BigDecimal changeAmount;

    private List<TransactionItemResponse> items;
}