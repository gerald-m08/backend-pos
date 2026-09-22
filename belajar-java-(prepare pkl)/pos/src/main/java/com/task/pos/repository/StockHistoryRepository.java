package com.task.pos.repository;

import com.task.pos.entity.StockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface StockHistoryRepository
        extends JpaRepository<StockHistory, UUID>,
        JpaSpecificationExecutor<StockHistory> {

    @EntityGraph(attributePaths = "product")
    Page<StockHistory> findAll(Specification<StockHistory> spec, Pageable pageable);
}