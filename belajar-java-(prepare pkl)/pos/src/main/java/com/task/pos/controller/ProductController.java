package com.task.pos.controller;

import com.task.pos.dto.common.ApiResponse;
import com.task.pos.dto.request.CreateProductRequest;
import com.task.pos.dto.request.UpdateProductRequest;
import com.task.pos.dto.response.PageResponse;
import com.task.pos.dto.response.ProductResponse;
import com.task.pos.enums.ProductStatus;
import com.task.pos.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse product = productService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Product created successfully", product));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> result = productService.getAll(
                search, categoryId, status, sortBy, direction, page, size
        );

        PageResponse pagination = new PageResponse(
                result.getNumber() + 1,
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
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable UUID id) {

        ProductResponse product = productService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product retrieved successfully", product)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable UUID id,
            @RequestBody UpdateProductRequest request
    ) {
        ProductResponse product = productService.update(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product updated successfully", product)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ProductResponse>> deactivate(@PathVariable UUID id) {

        ProductResponse product = productService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product deactivated successfully", product)
        );
    }
}