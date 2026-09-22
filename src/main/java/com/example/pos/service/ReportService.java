package com.example.pos.service;

import com.example.pos.dto.response.BestSellingProductResponse;
import com.example.pos.dto.response.DailySalesResponse;
import com.example.pos.dto.response.PaymentSummaryResponse;
import com.example.pos.entity.Transaction;
import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;
import com.example.pos.repository.TransactionItemRepository;
import com.example.pos.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;

    public DailySalesResponse getDailySales(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Transaction> transactions =
                transactionRepository.findByStatusAndTransactionDateBetween(
                        TransactionStatus.PAID,
                        start,
                        end
                );

        long totalTransaction = transactions.size();

        BigDecimal totalSales = transactions.stream()
                .map(Transaction::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DailySalesResponse(
                date,
                totalTransaction,
                totalSales
        );
    }

    public PaymentSummaryResponse getPaymentSummary(
            LocalDate startDate,
            LocalDate endDate
    ) {

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        BigDecimal cash =
                transactionRepository.sumTotalAmountByPaymentMethod(
                        TransactionStatus.PAID,
                        PaymentMethod.CASH,
                        start,
                        end
                );

        BigDecimal transfer =
                transactionRepository.sumTotalAmountByPaymentMethod(
                        TransactionStatus.PAID,
                        PaymentMethod.TRANSFER,
                        start,
                        end
                );

        BigDecimal qris =
                transactionRepository.sumTotalAmountByPaymentMethod(
                        TransactionStatus.PAID,
                        PaymentMethod.QRIS,
                        start,
                        end
                );

        BigDecimal card =
                transactionRepository.sumTotalAmountByPaymentMethod(
                        TransactionStatus.PAID,
                        PaymentMethod.CARD,
                        start,
                        end
                );

        return new PaymentSummaryResponse(
                cash,
                transfer,
                qris,
                card
        );
    }

    public List<BestSellingProductResponse> getBestSellingProducts(
            LocalDate date
    ) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return transactionItemRepository
                .findBestSellingProducts(
                        TransactionStatus.PAID,
                        start,
                        end
                )
                .stream()
                .map(row -> new BestSellingProductResponse(
                        (UUID) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }
}