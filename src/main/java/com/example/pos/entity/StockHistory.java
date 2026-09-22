package com.example.pos.entity;

import com.example.pos.enums.StockHistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_histories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
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

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}