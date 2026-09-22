package com.example.pos.repository;

import com.example.pos.entity.Transaction;
import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByCashierId(UUID cashierId);

    Page<Transaction> findByCashierId(UUID cashierId, Pageable pageable);

    @Query("""
        SELECT t FROM Transaction t
        WHERE LOWER(t.transactionNumber) LIKE LOWER(CONCAT('%', :search, '%'))
        AND t.transactionDate >= COALESCE(:startDate, t.transactionDate)
        AND t.transactionDate < :endDate
        AND t.cashier.id = COALESCE(:cashierId, t.cashier.id)
        AND t.paymentMethod = COALESCE(:paymentMethod, t.paymentMethod)
        AND t.status = COALESCE(:status, t.status)
        """)
    Page<Transaction> searchTransactions(
            @Param("search") String search,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("cashierId") UUID cashierId,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("status") TransactionStatus status,
            Pageable pageable
    );

    List<Transaction> findByStatusAndTransactionDateBetween(
            TransactionStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
            SELECT COALESCE(SUM(t.totalAmount), 0)
            FROM Transaction t
            WHERE t.status = :status
            AND t.paymentMethod = :paymentMethod
            AND t.transactionDate >= :start
            AND t.transactionDate < :end
            """)
    BigDecimal sumTotalAmountByPaymentMethod(
            @Param("status") TransactionStatus status,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}