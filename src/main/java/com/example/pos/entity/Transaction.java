package com.example.pos.entity;

import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "transactions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_transaction_number",
                        columnNames = "transaction_number"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "transaction_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String transactionNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cashier_id",
            nullable = false
    )
    private User cashier;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_method",
            nullable = false,
            length = 20
    )
    private PaymentMethod paymentMethod;

    @Column(
            name = "payment_reference",
            length = 100
    )
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private TransactionStatus status;

    @Column(
            precision = 15,
            scale = 2
    )
    private BigDecimal paymentAmount;

    @Column(
            precision = 15,
            scale = 2
    )
    private BigDecimal changeAmount;
}