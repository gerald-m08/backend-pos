package com.example.pos.dto.response;

import com.example.pos.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class ProductResponse {

    private UUID id;

    private String sku;

    private String productName;

    private UUID categoryId;

    private String categoryName;

    private BigDecimal price;

    private Integer stock;

    private ProductStatus status;
}