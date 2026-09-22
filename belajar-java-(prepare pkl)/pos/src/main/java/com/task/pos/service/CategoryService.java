package com.task.pos.service;

import com.task.pos.dto.request.CreateCategoryRequest;
import com.task.pos.dto.request.UpdateCategoryRequest;
import com.task.pos.dto.response.CategoryResponse;
import com.task.pos.entity.Category;
import com.task.pos.enums.CategoryStatus;
import com.task.pos.exception.ConflictException;
import com.task.pos.exception.ResourceNotFoundException;
import com.task.pos.mapper.CategoryMapper;
import com.task.pos.repository.CategoryRepository;
import com.task.pos.helper.PageableFactory;
import com.task.pos.helper.SortFieldValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "createdAt");

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SortFieldValidator sortFieldValidator;
    private final PageableFactory pageableFactory;

    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Category name already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(CategoryStatus.ACTIVE)
                .build();

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAll(String sortBy, String direction, int page, int size) {

        String validatedSortBy = sortFieldValidator.validate(sortBy, ALLOWED_SORT_FIELDS);
        Pageable pageable = pageableFactory.build(page, size, direction, validatedSortBy);

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse update(UUID id, UpdateCategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {

            throw new ConflictException("Category name already exists");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse deactivate(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        category.setStatus(CategoryStatus.INACTIVE);

        return categoryMapper.toResponse(categoryRepository.save(category));
    }
}