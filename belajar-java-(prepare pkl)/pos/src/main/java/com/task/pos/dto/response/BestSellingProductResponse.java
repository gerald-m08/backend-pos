package com.task.pos.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BestSellingProductResponse {

    private UUID productId;
    private String productName;
    private Long totalQuantity;
}