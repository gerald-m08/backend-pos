package com.task.pos.mapper;

import com.task.pos.dto.response.TransactionHistoryResponse;
import com.task.pos.dto.response.TransactionItemResponse;
import com.task.pos.dto.response.TransactionResponse;
import com.task.pos.entity.Transaction;
import com.task.pos.entity.TransactionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "processedBy.id", target = "processedById")
    TransactionResponse toResponse(Transaction transaction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    TransactionItemResponse toItemResponse(TransactionItem item);

    @Mapping(source = "processedBy.id", target = "processedById")
    TransactionHistoryResponse toHistoryResponse(Transaction transaction);
}