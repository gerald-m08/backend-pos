package com.task.pos.service;

import com.task.pos.dto.request.CreateProductRequest;
import com.task.pos.dto.request.UpdateProductRequest;
import com.task.pos.dto.response.ProductResponse;
import com.task.pos.entity.Category;
import com.task.pos.entity.Product;
import com.task.pos.enums.ProductStatus;
import com.task.pos.exception.ConflictException;
import com.task.pos.exception.ResourceNotFoundException;
import com.task.pos.helper.ProductSkuGenerator;
import com.task.pos.helper.SortFieldValidator;
import com.task.pos.mapper.ProductMapper;
import com.task.pos.repository.CategoryRepository;
import com.task.pos.repository.ProductRepository;
import com.task.pos.specification.ProductSpecification;
import com.task.pos.helper.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "price", "stock", "createdAt");

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductSkuGenerator productSkuGenerator;
    private final SortFieldValidator sortFieldValidator;
    private final PageableFactory pageableFactory;
    private final StockService stockService;

    @Transactional
    public ProductResponse create(CreateProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + request.getCategoryId()
                        )
                );

        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Product already exist");
        }


        Product product = Product.builder()
                .sku(productSkuGenerator.generate())
                .name(request.getName())
                .category(category)
                .price(request.getPrice())
                .stock(request.getStock())
                .status(ProductStatus.ACTIVE)
                .build();

        Product savedProduct = productRepository.save(product);

        stockService.recordInitialStock(savedProduct, request.getStock());

        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAll(
            String search,
            UUID categoryId,
            ProductStatus status,
            String sortBy,
            String direction,
            int page,
            int size
    ) {
        String validatedSortBy = sortFieldValidator.validate(sortBy, ALLOWED_SORT_FIELDS);
        Pageable pageable = pageableFactory.build(page, size, direction, validatedSortBy);

        var specification =
                ProductSpecification.search(search)
                        .and(ProductSpecification.hasCategory(categoryId))
                        .and(ProductSpecification.hasStatus(status));

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        return productPage.map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id)
                );

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse update(UUID id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id)
                );

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + request.getCategoryId()
                        )
                );

        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        // sku TIDAK diubah — bersifat permanen sejak dibuat

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Transactional
    public ProductResponse deactivate(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id)
                );

        product.setStatus(ProductStatus.INACTIVE);

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }
}