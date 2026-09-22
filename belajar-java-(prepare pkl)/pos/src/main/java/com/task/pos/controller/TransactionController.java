package com.task.pos.controller;

import com.task.pos.dto.common.ApiResponse;
import com.task.pos.dto.request.CreateTransactionRequest;
import com.task.pos.dto.response.PageResponse;
import com.task.pos.dto.response.TransactionHistoryResponse;
import com.task.pos.dto.response.TransactionResponse;
import com.task.pos.enums.PaymentMethod;
import com.task.pos.enums.TransactionStatus;
import com.task.pos.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        TransactionResponse response = transactionService.createTransaction(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Transaction created successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionHistoryResponse>>> getTransactions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID processedById,
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Page<TransactionHistoryResponse> result = transactionService.getTransactions(
                search, processedById, paymentMethod, status, startDate, endDate,
                page, limit, sortBy, sortDirection
        );

        PageResponse pagination = new PageResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true, "Success", result.getContent(), pagination
                )
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(
            @PathVariable UUID id
    ) {
        TransactionResponse transaction = transactionService.getTransactionById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Transaction retrieved successfully", transaction)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<ApiResponse<TransactionResponse>> confirmPayment(
            @PathVariable UUID id
    ) {
        TransactionResponse response = transactionService.confirmPayment(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Payment confirmed successfully", response)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<TransactionResponse>> cancelTransaction(
            @PathVariable UUID id
    ) {
        TransactionResponse response = transactionService.cancelTransaction(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Transaction cancelled successfully", response)
        );
    }
}

