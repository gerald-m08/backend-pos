package com.task.pos.repository.projection;

import java.util.UUID;

public interface BestSellingProductProjection {

    UUID getProductId();

    String getProductName();

    Long getTotalQuantity();
}