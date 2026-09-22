package com.task.pos.controller;

import com.task.pos.dto.common.ApiResponse;
import com.task.pos.dto.request.CreateCategoryRequest;
import com.task.pos.dto.request.UpdateCategoryRequest;
import com.task.pos.dto.response.CategoryResponse;
import com.task.pos.dto.response.PageResponse;
import com.task.pos.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CreateCategoryRequest request
    ) {
        CategoryResponse category = categoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Category created successfully", category));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CategoryResponse> result = categoryService.getAll(sortBy, direction, page, size);

        PageResponse pagination = new PageResponse(
                result.getNumber() + 1 ,
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Success", result.getContent(), pagination)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable UUID id) {

        CategoryResponse category = categoryService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category retrieved successfully", category)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        CategoryResponse category = categoryService.update(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category updated successfully", category)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<CategoryResponse>> deactivate(@PathVariable UUID id) {

        CategoryResponse category = categoryService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category deactivated successfully", category)
        );
    }
}