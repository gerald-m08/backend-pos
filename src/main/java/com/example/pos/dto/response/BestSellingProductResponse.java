package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BestSellingProductResponse {

    private UUID productId;
    private String productName;
    private Long totalQuantity;
}