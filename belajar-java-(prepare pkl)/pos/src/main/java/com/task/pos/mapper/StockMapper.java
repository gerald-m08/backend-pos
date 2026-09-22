package com.task.pos.mapper;

import com.task.pos.dto.response.StockHistoryResponse;
import com.task.pos.dto.response.StockResponse;
import com.task.pos.entity.Product;
import com.task.pos.entity.StockHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "name", target = "productName")
    StockResponse toStockResponse(Product product);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    StockHistoryResponse toHistoryResponse(StockHistory stockHistory);
}