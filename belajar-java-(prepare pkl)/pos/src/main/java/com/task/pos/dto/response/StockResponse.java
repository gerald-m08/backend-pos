package com.task.pos.dto.response;

import com.task.pos.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class StockResponse {

    private UUID productId;
    private String sku;
    private String productName;
    private Integer stock;
    private ProductStatus status;
    private LocalDateTime updatedAt;
}