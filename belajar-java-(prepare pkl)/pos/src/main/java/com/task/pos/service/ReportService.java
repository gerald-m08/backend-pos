package com.task.pos.service;

import com.task.pos.dto.response.BestSellingProductResponse;
import com.task.pos.dto.response.DailySalesResponse;
import com.task.pos.dto.response.PaymentSummaryResponse;
import com.task.pos.repository.TransactionItemRepository;
import com.task.pos.repository.TransactionRepository;
import com.task.pos.repository.projection.BestSellingProductProjection;
import com.task.pos.repository.projection.PaymentSummaryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;

    @Transactional(readOnly = true)
    public DailySalesResponse getDailySales(LocalDate startDate, LocalDate endDate) {

        validateDateRange(startDate, endDate);

        LocalDateTime startDateTime = toStartOfDay(startDate);
        LocalDateTime endDateTime = toExclusiveEndOfDay(endDate);

        Long totalTransaction =
                transactionRepository.countPaidTransactions(startDateTime, endDateTime);

        BigDecimal totalSales =
                transactionRepository.sumPaidTransactions(startDateTime, endDateTime);

        return DailySalesResponse.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalTransaction(totalTransaction)
                .totalSales(totalSales)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PaymentSummaryResponse> getPaymentSummary(LocalDate startDate, LocalDate endDate) {

        validateDateRange(startDate, endDate);

        LocalDateTime startDateTime = toStartOfDay(startDate);
        LocalDateTime endDateTime = toExclusiveEndOfDay(endDate);

        List<PaymentSummaryProjection> results =
                transactionRepository.getPaymentSummary(startDateTime, endDateTime);

        return results.stream()
                .map(result ->
                        PaymentSummaryResponse.builder()
                                .paymentMethod(result.getPaymentMethod())
                                .totalAmount(result.getTotalAmount())
                                .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BestSellingProductResponse> getBestSellingProducts(LocalDate startDate, LocalDate endDate) {

        validateDateRange(startDate, endDate);

        LocalDateTime startDateTime = toStartOfDay(startDate);
        LocalDateTime endDateTime = toExclusiveEndOfDay(endDate);

        List<BestSellingProductProjection> results =
                transactionItemRepository.getBestSellingProducts(startDateTime, endDateTime);

        return results.stream()
                .map(result ->
                        BestSellingProductResponse.builder()
                                .productId(result.getProductId())
                                .productName(result.getProductName())
                                .totalQuantity(result.getTotalQuantity())
                                .build()
                )
                .toList();
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date");
        }
    }

    private LocalDateTime toStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    private LocalDateTime toExclusiveEndOfDay(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }
}