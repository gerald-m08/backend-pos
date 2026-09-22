package com.task.pos.repository;

import com.task.pos.entity.TransactionItem;
import com.task.pos.repository.projection.BestSellingProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionItemRepository
        extends JpaRepository<TransactionItem, UUID> {

    @Query("""
            SELECT
                p.id AS productId,
                p.name AS productName,
                SUM(ti.quantity) AS totalQuantity
            FROM TransactionItem ti
            JOIN ti.transaction t
            JOIN ti.product p
            WHERE t.status = com.task.pos.enums.TransactionStatus.PAID
            AND t.transactionDate >= :startDate
            AND t.transactionDate < :endDate
            GROUP BY p.id, p.name
            ORDER BY SUM(ti.quantity) DESC
            """)
    List<BestSellingProductProjection> getBestSellingProducts(

            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}