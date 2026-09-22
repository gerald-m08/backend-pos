package com.task.pos.specification;

import com.task.pos.entity.Product;
import com.task.pos.enums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductSpecification {

    public static Specification<Product> search(
            String search
    ) {
        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }

            String keyword = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(
                            cb.lower(root.get("sku")),
                            keyword
                    ),
                    cb.like(
                            cb.lower(root.get("name")),
                            keyword
                    )
            );
        };
    }

    public static Specification<Product> hasCategory(
            UUID categoryId
    ) {
        return (root, query, cb) -> {

            if (categoryId == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("category").get("id"),
                    categoryId
            );
        };
    }

    public static Specification<Product> hasStatus(
            ProductStatus status
    ) {
        return (root, query, cb) -> {

            if (status == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("status"),
                    status
            );
        };
    }
}