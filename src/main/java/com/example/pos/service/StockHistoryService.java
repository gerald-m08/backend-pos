package com.example.pos.service;

import com.example.pos.dto.response.StockHistoryResponse;
import com.example.pos.entity.Product;
import com.example.pos.entity.StockHistory;
import com.example.pos.enums.StockHistoryType;
import com.example.pos.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistory create(
            Product product,
            Integer previousStock,
            Integer quantity,
            Integer finalStock,
            StockHistoryType type
    ) {

        StockHistory history = StockHistory.builder()
                .product(product)
                .previousStock(previousStock)
                .quantity(quantity)
                .finalStock(finalStock)
                .type(type)
                .build();

        return stockHistoryRepository.save(history);
    }

    public List<StockHistoryResponse> getByProduct(UUID productId) {

        return stockHistoryRepository.findAll()
                .stream()
                .filter(history ->
                        history.getProduct()
                                .getId()
                                .equals(productId)
                )
                .map(this::toResponse)
                .toList();
    }

    private StockHistoryResponse toResponse(StockHistory history) {

        return StockHistoryResponse.builder()
                .id(history.getId())
                .productId(history.getProduct().getId())
                .productName(history.getProduct().getProductName())
                .previousStock(history.getPreviousStock())
                .quantity(history.getQuantity())
                .finalStock(history.getFinalStock())
                .type(history.getType())
                .createdAt(history.getCreatedAt())
                .build();
    }
}