package com.example.pos.service;

import com.example.pos.dto.request.CategoryRequest;
import com.example.pos.dto.response.CategoryResponse;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.entity.Category;
import com.example.pos.enums.CategoryStatus;
import com.example.pos.exception.BadRequestException;
import com.example.pos.exception.ConflictException;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Category name already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(CategoryStatus.ACTIVE)
                .build();

        Category savedCategory = categoryRepository.save(category);

        return toResponse(savedCategory);
    }

    public PageResponse<CategoryResponse> getAll(
            int page,
            int limit,
            String sortBy,
            String sortDirection
    ) {
        String sortField = switch (sortBy.toLowerCase()) {
            case "name" -> "name";
            case "status" -> "status";
            default -> "createdAt";
        };

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> data = categoryPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return PageResponse.<CategoryResponse>builder()
                .page(page)
                .limit(limit)
                .totalData(categoryPage.getTotalElements())
                .totalPage(categoryPage.getTotalPages())
                .data(data)
                .build();
    }

    public CategoryResponse getById(UUID id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );

        return toResponse(category);
    }

    public CategoryResponse update(
            UUID id,
            CategoryRequest request
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {

            throw new BadRequestException(
                    "Category already exists"
            );
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory =
                categoryRepository.save(category);

        return toResponse(updatedCategory);
    }

    public void deactivate(UUID id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );

        category.setStatus(CategoryStatus.INACTIVE);

        categoryRepository.save(category);
    }

    private CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
    }
}