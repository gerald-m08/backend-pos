package com.task.pos.controller;

import com.task.pos.dto.common.ApiResponse;
import com.task.pos.dto.response.BestSellingProductResponse;
import com.task.pos.dto.response.DailySalesResponse;
import com.task.pos.dto.response.PaymentSummaryResponse;
import com.task.pos.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales/daily")
    public ResponseEntity<ApiResponse<DailySalesResponse>> getDailySales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        DailySalesResponse response = reportService.getDailySales(startDate, endDate);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Daily sales report retrieved successfully", response)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales/payment-summary")
    public ResponseEntity<ApiResponse<List<PaymentSummaryResponse>>> getPaymentSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<PaymentSummaryResponse> response = reportService.getPaymentSummary(startDate, endDate);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Payment summary retrieved successfully", response)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales/best-selling-products")
    public ResponseEntity<ApiResponse<List<BestSellingProductResponse>>> getBestSellingProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<BestSellingProductResponse> response = reportService.getBestSellingProducts(startDate, endDate);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Best selling products retrieved successfully", response)
        );
    }
}