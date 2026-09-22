package com.example.pos.controller;

import com.example.pos.dto.request.PaymentRequest;
import com.example.pos.dto.request.TransactionRequest;
import com.example.pos.dto.request.TransactionStatusRequest;
import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.dto.response.TransactionResponse;
import com.example.pos.service.PaymentService;
import com.example.pos.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> create(
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Transaction created successfully",
                                transactionService.create(request)
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TransactionResponse>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) UUID cashierId,
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Transactions retrieved successfully",
                        transactionService.getAll(
                                page,
                                limit,
                                search,
                                startDate,
                                endDate,
                                cashierId,
                                paymentMethod,
                                status,
                                sortBy,
                                sortDirection
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Transaction retrieved successfully",
                        transactionService.getById(id)
                )
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody TransactionStatusRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Transaction status updated successfully",
                        transactionService.updateStatus(
                                id,
                                request.getStatus()
                        )
                )
        );
    }

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<TransactionResponse>> pay(
            @Valid @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment completed successfully",
                        paymentService.pay(request)
                )
        );
    }
}