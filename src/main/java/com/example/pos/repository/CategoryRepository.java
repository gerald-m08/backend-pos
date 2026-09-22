package com.example.pos.repository;

import com.example.pos.entity.Category;
import com.example.pos.enums.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository
        extends JpaRepository<Category, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Category> findByNameIgnoreCase(String name);

    Optional<Category> findByIdAndStatus(
            UUID id,
            CategoryStatus status
    );
}