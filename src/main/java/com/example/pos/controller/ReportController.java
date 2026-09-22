package com.example.pos.controller;

import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.BestSellingProductResponse;
import com.example.pos.dto.response.DailySalesResponse;
import com.example.pos.dto.response.PaymentSummaryResponse;
import com.example.pos.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales/daily")
    public ResponseEntity<ApiResponse<DailySalesResponse>> getDailySales(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Daily sales report retrieved successfully",
                        reportService.getDailySales(date)
                )
        );
    }

    @GetMapping("/sales/payment-summary")
    public ResponseEntity<ApiResponse<PaymentSummaryResponse>> getPaymentSummary(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment summary retrieved successfully",
                        reportService.getPaymentSummary(startDate, endDate)
                )
        );
    }

    @GetMapping("/sales/best-selling-products")
    public ResponseEntity<ApiResponse<List<BestSellingProductResponse>>> getBestSellingProducts(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Best selling products report retrieved successfully",
                        reportService.getBestSellingProducts(date)
                )
        );
    }
}