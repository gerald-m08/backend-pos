package com.task.pos.service;

import com.task.pos.dto.request.StockAdjustmentRequest;
import com.task.pos.dto.response.StockHistoryResponse;
import com.task.pos.dto.response.StockResponse;
import com.task.pos.entity.Product;
import com.task.pos.entity.StockHistory;
import com.task.pos.enums.ProductStatus;
import com.task.pos.enums.StockHistoryType;
import com.task.pos.exception.ConflictException;
import com.task.pos.exception.ResourceNotFoundException;
import com.task.pos.helper.SortFieldValidator;
import com.task.pos.mapper.StockMapper;
import com.task.pos.repository.ProductRepository;
import com.task.pos.repository.StockHistoryRepository;
import com.task.pos.specification.ProductSpecification;
import com.task.pos.specification.StockHistorySpecification;
import com.task.pos.helper.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockService {

    private static final Set<String> ALLOWED_STOCK_SORT_FIELDS = Set.of("name", "stock", "updatedAt");

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockMapper stockMapper;
    private final SortFieldValidator sortFieldValidator;
    private final PageableFactory pageableFactory;


    @Transactional
    public void recordInitialStock(Product product, int initialQuantity) {

        if (initialQuantity <= 0) {
            return;
        }

        StockHistory history = StockHistory.builder()
                .product(product)
                .previousStock(0)
                .quantity(initialQuantity)
                .finalStock(initialQuantity)
                .type(StockHistoryType.IN)
                .note("Initial stock upon product creation")
                .build();

        stockHistoryRepository.save(history);
    }


    public void validateStock(Product product, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (product.getStock() < quantity) {
            throw new ConflictException("Insufficient stock");
        }
    }

    @Transactional
    public void decreaseStock(UUID productId, int quantity) {

        Product product = lockProductForUpdate(productId);

        validateStock(product, quantity);

        int previousStock = product.getStock();
        int finalStock = previousStock - quantity;

        recordStockChange(product, previousStock, quantity, finalStock, StockHistoryType.OUT, null);
    }


    @Transactional
    public void restoreStock(UUID productId, int quantity) {

        Product product = lockProductForUpdate(productId);

        int previousStock = product.getStock();
        int finalStock = previousStock + quantity;

        recordStockChange(
                product, previousStock, quantity, finalStock,
                StockHistoryType.RETURN, "Stock restored due to transaction cancellation"
        );
    }

    @Transactional
    public StockResponse adjustStock(UUID productId, StockAdjustmentRequest request) {

        Product product = applyAdjustment(
                productId,
                request.getQuantity(),
                request.getType(),
                request.getNote()
        );

        return stockMapper.toStockResponse(product);
    }

    @Transactional(readOnly = true)
    public StockResponse getStockByProductId(UUID productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + productId)
                );

        return stockMapper.toStockResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<StockResponse> getAllStock(
            String search,
            UUID categoryId,
            ProductStatus status,
            String sortBy,
            String direction,
            int page,
            int size
    ) {
        String validatedSortBy = sortFieldValidator.validate(sortBy, ALLOWED_STOCK_SORT_FIELDS);
        Pageable pageable = pageableFactory .build(page, size, direction, validatedSortBy);

        Specification<Product> specification =
                ProductSpecification.search(search)
                        .and(ProductSpecification.hasCategory(categoryId))
                        .and(ProductSpecification.hasStatus(status));

        return productRepository.findAll(specification, pageable)
                .map(stockMapper::toStockResponse);
    }

    @Transactional(readOnly = true)
    public Page<StockHistoryResponse> getStockHistory(
            UUID productId,
            StockHistoryType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size
    ) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        Pageable pageable = pageableFactory.build(page, size, "desc", "createdAt");

        Specification<StockHistory> specification =
                StockHistorySpecification.hasProduct(productId)
                        .and(StockHistorySpecification.hasType(type))
                        .and(StockHistorySpecification.createdBetween(startDate, endDate));

        return stockHistoryRepository.findAll(specification, pageable)
                .map(stockMapper::toHistoryResponse);
    }

    private Product applyAdjustment(UUID productId, int quantity, StockHistoryType type, String note) {

        if (type == StockHistoryType.OUT || type == StockHistoryType.RETURN) {
            throw new IllegalArgumentException(
                    "OUT and RETURN types are system-managed and cannot be created manually"
            );
        }

        if (type == StockHistoryType.IN && quantity <= 0) {
            throw new IllegalArgumentException("Quantity for IN must be greater than 0");
        }

        if (type == StockHistoryType.ADJUSTMENT && quantity == 0) {
            throw new IllegalArgumentException("Adjustment quantity cannot be zero");
        }

        Product product = lockProductForUpdate(productId);

        int previousStock = product.getStock();
        int finalStock = previousStock + quantity;

        if (finalStock < 0) {
            throw new ConflictException(
                    "Stock cannot be negative after adjustment (current: "
                            + previousStock + ", requested: " + quantity + ")"
            );
        }

        recordStockChange(product, previousStock, quantity, finalStock, type, note);

        return product;
    }

    private void recordStockChange(
            Product product,
            int previousStock,
            int quantity,
            int finalStock,
            StockHistoryType type,
            String note
    ) {
        product.setStock(finalStock);
        productRepository.save(product);

        StockHistory history = StockHistory.builder()
                .product(product)
                .previousStock(previousStock)
                .quantity(quantity)
                .finalStock(finalStock)
                .type(type)
                .note(note)
                .build();

        stockHistoryRepository.save(history);
    }

    private Product lockProductForUpdate(UUID productId) {
        return productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}