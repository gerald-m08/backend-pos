package com.example.pos.controller;

import com.example.pos.dto.request.CategoryRequest;
import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.CategoryResponse;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Category created successfully",
                                categoryService.create(request)
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Categories retrieved successfully",
                        categoryService.getAll(
                                page,
                                limit,
                                sortBy,
                                sortDirection
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category retrieved successfully",
                        categoryService.getById(id)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category updated successfully",
                        categoryService.update(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivate(
            @PathVariable UUID id
    ) {
        categoryService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category deactivated successfully",
                        null
                )
        );
    }
}