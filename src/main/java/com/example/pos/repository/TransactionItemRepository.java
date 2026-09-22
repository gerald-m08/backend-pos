package com.example.pos.repository;

import com.example.pos.entity.TransactionItem;
import com.example.pos.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionItemRepository
        extends JpaRepository<TransactionItem, UUID> {

    List<TransactionItem> findByTransactionId(
            UUID transactionId
    );

    @Query("""
        SELECT ti.product.id,
               ti.product.productName,
               SUM(ti.quantity)
        FROM TransactionItem ti
        WHERE ti.transaction.status = :status
        AND ti.transaction.transactionDate >= :start
        AND ti.transaction.transactionDate < :end
        GROUP BY ti.product.id, ti.product.productName
        ORDER BY SUM(ti.quantity) DESC
        """)
    List<Object[]> findBestSellingProducts(
            TransactionStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}