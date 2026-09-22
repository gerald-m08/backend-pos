package com.task.pos.entity;

import com.task.pos.enums.StockHistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "stock_histories",
        indexes = {
                @Index(name = "idx_stock_history_product", columnList = "product_id"),
                @Index(name = "idx_stock_history_created_at", columnList = "created_at"),
                @Index(name = "idx_stock_history_type", columnList = "type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer previousStock;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer finalStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StockHistoryType type;

    @Column(columnDefinition = "TEXT")
    private String note; // baru

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}