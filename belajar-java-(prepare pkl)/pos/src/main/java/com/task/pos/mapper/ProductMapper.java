package com.task.pos.mapper;

import com.task.pos.dto.response.ProductResponse;
import com.task.pos.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

    ProductResponse toProductResponse(Product entity);

    ProductResponse toProductResponseFromEntity(Product entity);

    ProductResponse toProductResponseFromDto(Product dto);

}