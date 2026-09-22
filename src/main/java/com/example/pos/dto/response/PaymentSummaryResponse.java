package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PaymentSummaryResponse {

    private BigDecimal cash;
    private BigDecimal transfer;
    private BigDecimal qris;
    private BigDecimal card;
}