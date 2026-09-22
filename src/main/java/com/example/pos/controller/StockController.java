package com.example.pos.controller;

import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.dto.response.StockHistoryResponse;
import com.example.pos.service.StockHistoryService;
import com.example.pos.service.StockService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StockController {

    private final StockService stockService;
    private final StockHistoryService stockHistoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllStock(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Stock retrieved successfully",
                        stockService.getAllStock(
                                page,
                                limit,
                                sortBy,
                                sortDirection
                        )
                )
        );
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<ApiResponse<ProductResponse>> getStockById(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Stock retrieved successfully",
                        stockService.getStockById(productId)
                )
        );
    }

    @GetMapping("/history/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StockHistoryResponse>>> getStockHistory(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Stock history retrieved successfully",
                        stockHistoryService.getByProduct(productId)
                )
        );
    }
}