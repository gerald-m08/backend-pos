package com.task.pos.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class DailySalesResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalTransaction;
    private BigDecimal totalSales;
}