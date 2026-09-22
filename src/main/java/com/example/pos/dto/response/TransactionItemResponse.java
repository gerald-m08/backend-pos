package com.example.pos.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class TransactionItemResponse {

    private UUID id;

    private UUID productId;

    private String productName;

    private String sku;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}