package com.example.pos.repository;

import com.example.pos.entity.Product;
import com.example.pos.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsBySkuIgnoreCase(String sku);

    @Query("""
            SELECT p
            FROM Product p
            WHERE
                (:status IS NULL OR p.status = :status)
            AND
                (:categoryId IS NULL OR p.category.id = :categoryId)
            AND
                (
                    :search IS NULL
                    OR :search = ''
                    OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """)
    Page<Product> searchProducts(
            @Param("search") String search,
            @Param("categoryId") UUID categoryId,
            @Param("status") ProductStatus status,
            Pageable pageable
    );
}