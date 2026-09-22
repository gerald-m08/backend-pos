package com.example.pos.service;

import com.example.pos.dto.response.PageResponse;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.entity.Product;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.ProductRepository;
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
public class StockService {

    private final ProductRepository productRepository;

    public PageResponse<ProductResponse> getAllStock(
            int page,
            int limit,
            String sortBy,
            String sortDirection
    ) {
        String sortField = switch (sortBy.toLowerCase()) {
            case "name" -> "productName";
            case "price" -> "price";
            case "stock" -> "stock";
            default -> "createdAt";
        };

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> data = productPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .page(page)
                .limit(limit)
                .totalData(productPage.getTotalElements())
                .totalPage(productPage.getTotalPages())
                .data(data)
                .build();
    }

    public ProductResponse getStockById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return toResponse(product);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .build();
    }
}