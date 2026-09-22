package com.task.pos.mapper;

import com.task.pos.dto.response.CategoryResponse;
import com.task.pos.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);
}