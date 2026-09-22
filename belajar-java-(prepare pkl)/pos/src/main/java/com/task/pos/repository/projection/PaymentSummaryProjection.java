package com.task.pos.repository.projection;

import com.task.pos.enums.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentSummaryProjection {

    PaymentMethod getPaymentMethod();

    BigDecimal getTotalAmount();
}