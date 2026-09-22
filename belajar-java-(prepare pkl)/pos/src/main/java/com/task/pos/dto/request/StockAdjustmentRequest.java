package com.task.pos.dto.request;

import com.task.pos.enums.StockHistoryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockAdjustmentRequest {

    @NotNull(message = "Adjustment type is required")
    private StockHistoryType type;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Size(max = 255, message = "Note must not exceed 255 characters")
    private String note;
}