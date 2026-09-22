package com.example.pos.repository;

import com.example.pos.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockHistoryRepository
        extends JpaRepository<StockHistory, UUID> {
}