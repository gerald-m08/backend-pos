package com.task.pos.controller;

import com.task.pos.dto.common.ApiResponse;
import com.task.pos.dto.request.StockAdjustmentRequest;
import com.task.pos.dto.response.PageResponse;
import com.task.pos.dto.response.StockHistoryResponse;
import com.task.pos.dto.response.StockResponse;
import com.task.pos.enums.ProductStatus;
import com.task.pos.enums.StockHistoryType;
import com.task.pos.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<StockResponse>> getStockByProductId(
            @PathVariable UUID productId
    ) {
        StockResponse stock = stockService.getStockByProductId(productId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Stock retrieved successfully", stock)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> getAllStock(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<StockResponse> result = stockService.getAllStock(
                search, categoryId, status, sortBy, direction, page, size
        );

        PageResponse pagination = new PageResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", result.getContent(), pagination)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<StockResponse>> adjustStock(
            @PathVariable UUID productId,
            @Valid @RequestBody StockAdjustmentRequest request
    ) {
        StockResponse stock = stockService.adjustStock(productId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Stock adjusted successfully", stock)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{productId}/history")
    public ResponseEntity<ApiResponse<List<StockHistoryResponse>>> getStockHistory(
            @PathVariable UUID productId,
            @RequestParam(required = false) StockHistoryType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<StockHistoryResponse> result =
                stockService.getStockHistory(productId, type, startDate, endDate, page, size);

        PageResponse pagination = new PageResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Stock history retrieved successfully", result.getContent(), pagination)
        );
    }
}