package com.task.pos.dto.response;

import com.task.pos.enums.StockHistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class StockHistoryResponse {

    private UUID id;
    private UUID productId;
    private String productName;
    private Integer previousStock;
    private Integer quantity;
    private Integer finalStock;
    private StockHistoryType type;
    private String note;
    private LocalDateTime createdAt;
}