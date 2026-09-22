package com.task.pos.repository;

import com.task.pos.entity.Transaction;
import com.task.pos.enums.TransactionStatus;
import com.task.pos.repository.projection.PaymentSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>,
        JpaSpecificationExecutor<Transaction> {

    boolean existsByTransactionNumber(String transactionNumber);

    long countByStatus(TransactionStatus status);

    @EntityGraph(attributePaths = {"processedBy", "items", "items.product"})
    Optional<Transaction> findById(UUID id);

    @EntityGraph(attributePaths = "processedBy")
    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);

    @Query("""
        SELECT COUNT(t)
        FROM Transaction t
        WHERE t.status = com.task.pos.enums.TransactionStatus.PAID
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        """)
    Long countPaidTransactions(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT COALESCE(SUM(t.totalAmount), 0)
        FROM Transaction t
        WHERE t.status = com.task.pos.enums.TransactionStatus.PAID
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        """)
    BigDecimal sumPaidTransactions(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT
            t.paymentMethod AS paymentMethod,
            COALESCE(SUM(t.totalAmount), 0) AS totalAmount
        FROM Transaction t
        WHERE t.status = com.task.pos.enums.TransactionStatus.PAID
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        GROUP BY t.paymentMethod
        ORDER BY t.paymentMethod
        """)
    List<PaymentSummaryProjection> getPaymentSummary(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}