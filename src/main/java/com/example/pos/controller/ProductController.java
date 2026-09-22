package com.example.pos.controller;

import com.example.pos.dto.request.ProductRequest;
import com.example.pos.dto.request.ProductUpdateRequest;
import com.example.pos.dto.request.StockRequest;
import com.example.pos.dto.response.ApiResponse;
import com.example.pos.dto.response.ProductPageResponse;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.enums.ProductStatus;
import com.example.pos.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<ApiResponse<ProductPageResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Products retrieved successfully",
                        productService.getAll(
                                page,
                                limit,
                                search,
                                categoryId,
                                status,
                                sortBy,
                                sortDirection
                        )
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product retrieved successfully",
                        productService.getById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Product created successfully",
                                productService.create(request)
                        )
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product updated successfully",
                        productService.update(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivate(
            @PathVariable UUID id
    ) {
        productService.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product deactivated successfully",
                        null
                )
        );
    }

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateStock(
            @PathVariable UUID id,
            @Valid @RequestBody StockRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Stock updated successfully",
                        productService.updateStock(id, request)
                )
        );
    }
}