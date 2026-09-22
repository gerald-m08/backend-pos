package com.task.pos.specification;

import com.task.pos.entity.StockHistory;
import com.task.pos.enums.StockHistoryType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockHistorySpecification {

    public static Specification<StockHistory> hasProduct(UUID productId) {
        return (root, query, cb) ->
                cb.equal(root.get("product").get("id"), productId);
    }

    public static Specification<StockHistory> hasType(StockHistoryType type) {
        return (root, query, cb) -> {
            if (type == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("type"), type);
        };
    }

    public static Specification<StockHistory> createdBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if (startDate != null) {
                predicate = cb.and(
                        predicate,
                        cb.greaterThanOrEqualTo(root.get("createdAt"), startDate)
                );
            }

            if (endDate != null) {
                predicate = cb.and(
                        predicate,
                        cb.lessThanOrEqualTo(root.get("createdAt"), endDate)
                );
            }

            return predicate;
        };
    }
}