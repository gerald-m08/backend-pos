package com.task.pos.dto.response;

import com.task.pos.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ProductResponse {

    private UUID id;
    private String sku;
    private String name;

    private UUID categoryId;
    private String categoryName;

    private BigDecimal price;
    private Integer stock;
    private ProductStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}