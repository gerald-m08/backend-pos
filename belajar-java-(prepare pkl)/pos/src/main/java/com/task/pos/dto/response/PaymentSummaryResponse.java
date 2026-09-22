package com.task.pos.dto.response;

import com.task.pos.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentSummaryResponse {

    private PaymentMethod paymentMethod;
    private BigDecimal totalAmount;
}