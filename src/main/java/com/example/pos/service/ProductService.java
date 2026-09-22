package com.example.pos.service;

import com.example.pos.dto.request.ProductRequest;
import com.example.pos.dto.request.ProductUpdateRequest;
import com.example.pos.dto.request.StockRequest;
import com.example.pos.dto.response.ProductPageResponse;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.entity.Category;
import com.example.pos.entity.Product;
import com.example.pos.enums.CategoryStatus;
import com.example.pos.enums.ProductStatus;
import com.example.pos.exception.BadRequestException;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.CategoryRepository;
import com.example.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StockHistoryService stockHistoryService;

    public ProductResponse create(ProductRequest request) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found")
                );

        if (category.getStatus() != CategoryStatus.ACTIVE) {
            throw new BadRequestException("Category is inactive");
        }

        String sku = generateSku();

        Product product = Product.builder()
                .sku(sku)
                .productName(request.getProductName())
                .category(category)
                .price(request.getPrice())
                .stock(request.getStock())
                .status(ProductStatus.ACTIVE)
                .build();

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    public ProductPageResponse getAll(
            int page,
            int limit,
            String search,
            UUID categoryId,
            ProductStatus status,
            String sortBy,
            String sortDirection
    ) {
        String sortField = switch (sortBy.toLowerCase()) {
            case "name" -> "productName";
            case "price" -> "price";
            case "stock" -> "stock";
            case "createddate" -> "createdAt";
            default -> "createdAt";
        };

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Product> productPage = productRepository.searchProducts(
                search,
                categoryId,
                status,
                pageable
        );

        List<ProductResponse> data = productPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return ProductPageResponse.builder()
                .page(page)
                .limit(limit)
                .totalData(productPage.getTotalElements())
                .totalPage(productPage.getTotalPages())
                .data(data)
                .build();
    }

    public ProductResponse getById(UUID id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        return toResponse(product);
    }

    public ProductResponse update(
            UUID id,
            ProductUpdateRequest request
    ) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found")
                );

        if (category.getStatus() != CategoryStatus.ACTIVE) {
            throw new BadRequestException("Category is inactive");
        }

        product.setProductName(request.getProductName());
        product.setCategory(category);
        product.setPrice(request.getPrice());

        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }

    public void deactivate(UUID id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        product.setStatus(ProductStatus.INACTIVE);
        productRepository.save(product);
    }

    @Transactional
    public ProductResponse updateStock(
            UUID productId,
            StockRequest request
    ) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        int previousStock = product.getStock();
        int quantity = request.getQuantity();
        int finalStock;

        switch (request.getType()) {
            case IN:
                finalStock = previousStock + quantity;
                break;

            case OUT:
                if (previousStock < quantity) {
                    throw new BadRequestException("Insufficient stock");
                }
                finalStock = previousStock - quantity;
                break;

            case ADJUSTMENT:
                finalStock = quantity;
                break;

            default:
                throw new BadRequestException("Invalid stock type");
        }

        product.setStock(finalStock);

        Product savedProduct = productRepository.save(product);

        stockHistoryService.create(
                product,
                previousStock,
                quantity,
                finalStock,
                request.getType()
        );

        return toResponse(savedProduct);
    }

    private String generateSku() {

        String sku;

        do {
            sku = "SKU-" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .toUpperCase();
        } while (productRepository.existsBySkuIgnoreCase(sku));

        return sku;
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