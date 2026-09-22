package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailySalesResponse {

    private LocalDate date;
    private long totalTransaction;
    private BigDecimal totalSales;
}