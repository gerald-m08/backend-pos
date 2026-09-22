package com.task.pos.specification;

import com.task.pos.entity.Transaction;
import com.task.pos.enums.PaymentMethod;
import com.task.pos.enums.TransactionStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionSpecification {

    public static Specification<Transaction> search(
            String search,
            UUID processedById,
            PaymentMethod paymentMethod,
            TransactionStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();

            if (search != null && !search.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("transactionNumber")),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            if (processedById != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(
                                root.get("processedBy").get("id"),
                                processedById
                        )
                );
            }

            if (paymentMethod != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod)
                );
            }

            if (status != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            if (startDate != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), startDate)
                );
            }

            if (endDate != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), endDate)
                );
            }

            return predicates;
        };
    }
}