package com.example.pos.service;

import com.example.pos.dto.request.TransactionItemRequest;
import com.example.pos.dto.request.TransactionRequest;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.dto.response.TransactionItemResponse;
import com.example.pos.dto.response.TransactionResponse;
import com.example.pos.entity.*;
import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.ProductStatus;
import com.example.pos.enums.StockHistoryType;
import com.example.pos.enums.TransactionStatus;
import com.example.pos.exception.BadRequestException;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.ProductRepository;
import com.example.pos.repository.TransactionItemRepository;
import com.example.pos.repository.TransactionRepository;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockHistoryService stockHistoryService;

    @Transactional
    public TransactionResponse create(TransactionRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }

        String username = authentication.getName();

        User cashier = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cashier not found")
                );

        String transactionNumber = generateTransactionNumber();

        Transaction transaction = Transaction.builder()
                .transactionNumber(transactionNumber)
                .cashier(cashier)
                .transactionDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .paymentMethod(request.getPaymentMethod())
                .paymentReference(null)
                .status(TransactionStatus.PENDING)
                .paymentAmount(request.getPaymentAmount())
                .changeAmount(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<TransactionItem> items = new ArrayList<>();

        for (TransactionItemRequest itemRequest : request.getItems()) {

            Product product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product not found")
                    );

            if (product.getStatus() != ProductStatus.ACTIVE) {
                throw new BadRequestException(
                        "Product is inactive and cannot be purchased"
                );
            }

            int previousStock = product.getStock();

            if (previousStock < itemRequest.getQuantity()) {
                throw new BadRequestException(
                        "Insufficient stock for product: "
                                + product.getProductName()
                );
            }

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(itemRequest.getQuantity())
            );

            totalAmount = totalAmount.add(subtotal);

            TransactionItem transactionItem = TransactionItem.builder()
                    .transaction(transaction)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            items.add(transactionItem);
        }

        BigDecimal paymentAmount = request.getPaymentAmount();
        BigDecimal changeAmount = BigDecimal.ZERO;
        String paymentReference = null;
        TransactionStatus transactionStatus;

        if (request.getPaymentMethod() == PaymentMethod.CASH) {

            if (paymentAmount == null) {
                throw new BadRequestException(
                        "Payment amount is required for cash payment"
                );
            }

            if (paymentAmount.compareTo(totalAmount) < 0) {
                throw new BadRequestException(
                        "Payment amount is insufficient"
                );
            }

            changeAmount = paymentAmount.subtract(totalAmount);
            transactionStatus = TransactionStatus.PAID;

        } else {

            if (paymentAmount == null) {
                throw new BadRequestException(
                        "Payment amount is required"
                );
            }

            if (paymentAmount.compareTo(totalAmount) != 0) {
                throw new BadRequestException(
                        "Payment amount must be equal to total amount"
                );
            }

            paymentReference = generatePaymentReference(
                    request.getPaymentMethod()
            );

            transactionStatus = TransactionStatus.PENDING;
        }

        transaction.setTotalAmount(totalAmount);
        transaction.setPaymentAmount(paymentAmount);
        transaction.setPaymentReference(paymentReference);
        transaction.setChangeAmount(changeAmount);
        transaction.setStatus(transactionStatus);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        List<TransactionItem> savedItems = new ArrayList<>();

        for (TransactionItem item : items) {

            item.setTransaction(savedTransaction);

            TransactionItem savedItem =
                    transactionItemRepository.save(item);

            savedItems.add(savedItem);
        }

        /*
         * Stock langsung berkurang ketika transaction dibuat.
         */
        for (TransactionItem item : savedItems) {

            Product product = item.getProduct();
            int previousStock = product.getStock();
            int finalStock = previousStock - item.getQuantity();

            product.setStock(finalStock);
            productRepository.save(product);

            stockHistoryService.create(
                    product,
                    previousStock,
                    item.getQuantity(),
                    finalStock,
                    StockHistoryType.OUT
            );
        }

        return toResponse(savedTransaction, savedItems);
    }

    public PageResponse<TransactionResponse> getAll(
            int page,
            int limit,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            UUID cashierId,
            PaymentMethod paymentMethod,
            TransactionStatus status,
            String sortBy,
            String sortDirection
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }

        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (page < 1) {
            throw new BadRequestException("Page must be greater than 0");
        }

        if (limit < 1) {
            throw new BadRequestException("Limit must be greater than 0");
        }

        String sortField = switch (sortBy.toLowerCase()) {
            case "transactionnumber" -> "transactionNumber";
            case "totalamount" -> "totalAmount";
            case "status" -> "status";
            case "paymentmethod" -> "paymentMethod";
            default -> "transactionDate";
        };

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        LocalDateTime startDateTime = startDate != null
                ? startDate.atStartOfDay()
                : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime endDateTime = endDate != null
                ? endDate.plusDays(1).atStartOfDay()
                : LocalDateTime.of(9999, 12, 31, 23, 59, 59);

        String searchValue = search != null ? search : "";

        UUID filterCashierId;

        if (currentUser.getRole() == Role.ADMIN) {
            filterCashierId = cashierId;
        } else {
            filterCashierId = currentUser.getId();
        }

        Page<Transaction> transactionPage = transactionRepository.searchTransactions(
                searchValue,
                startDateTime,
                endDateTime,
                filterCashierId,
                paymentMethod,
                status,
                pageable
        );

        List<TransactionResponse> data = transactionPage.getContent().stream()
                .map(transaction -> {
                    List<TransactionItem> items = transactionItemRepository.findByTransactionId(transaction.getId());
                    return toResponse(transaction, items);
                })
                  .toList();

        return PageResponse.<TransactionResponse>builder()
                .page(page)
                .limit(limit)
                .totalData(transactionPage.getTotalElements())
                .totalPage(transactionPage.getTotalPages())
                .data(data)
                .build();
    }

    public TransactionResponse getById(UUID id) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }

        String username = authentication.getName();

        User currentUser =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found")
                        );

        if (!currentUser.getRole().name().equals("ADMIN")
                && !transaction.getCashier().getId().equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You are not allowed to view this transaction"
            );
        }

        List<TransactionItem> items =
                transactionItemRepository.findByTransactionId(
                        transaction.getId()
                );

        return toResponse(transaction, items);
    }

    private String generateTransactionNumber() {

        String date = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        return "TRX-" + date + "-" + random;
    }

    private String generatePaymentReference(
            PaymentMethod paymentMethod
    ) {
        String prefix = switch (paymentMethod) {
            case QRIS -> "QRIS";
            case CARD -> "CARD";
            case TRANSFER -> "TRF";
            default -> "PAY";
        };

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        return prefix + "-" + random;
    }

    private TransactionResponse toResponse(
            Transaction transaction,
            List<TransactionItem> items
    ) {
        List<TransactionItemResponse> itemResponses = items.stream()
                .map(this::toItemResponse)
                .toList();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionNumber(transaction.getTransactionNumber())
                .cashierId(transaction.getCashier().getId())
                .cashierUsername(transaction.getCashier().getUsername())
                .transactionDate(transaction.getTransactionDate())
                .totalAmount(transaction.getTotalAmount())
                .paymentMethod(transaction.getPaymentMethod())
                .paymentReference(transaction.getPaymentReference())
                .status(transaction.getStatus())
                .paymentAmount(transaction.getPaymentAmount())
                .changeAmount(transaction.getChangeAmount())
                .items(itemResponses)
                .build();
    }

    private TransactionItemResponse toItemResponse(TransactionItem item) {

        return TransactionItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getProductName())
                .sku(item.getProduct().getSku())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }

    @Transactional
    public TransactionResponse updateStatus(
            UUID id,
            TransactionStatus newStatus
    ) {
        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }

        String username = authentication.getName();

        User currentUser =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found")
                        );

        if (!currentUser.getRole().name().equals("ADMIN")
                && !transaction.getCashier().getId().equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You are not allowed to update this transaction"
            );
        }

        TransactionStatus currentStatus = transaction.getStatus();

        if (currentStatus == TransactionStatus.PAID
                || currentStatus == TransactionStatus.CANCELLED) {

            throw new BadRequestException(
                    "Transaction status cannot be changed from "
                            + currentStatus
            );
        }

        if (currentStatus == TransactionStatus.PENDING
                && newStatus == TransactionStatus.CANCELLED) {

            List<TransactionItem> items =
                    transactionItemRepository.findByTransactionId(id);

            /*
             * Stock dikembalikan ketika transaction dibatalkan.
             */
            for (TransactionItem item : items) {

                Product product = item.getProduct();
                int previousStock = product.getStock();
                int finalStock = previousStock + item.getQuantity();

                product.setStock(finalStock);
                productRepository.save(product);

                stockHistoryService.create(
                        product,
                        previousStock,
                        item.getQuantity(),
                        finalStock,
                        StockHistoryType.IN
                );
            }

            transaction.setStatus(TransactionStatus.CANCELLED);

            Transaction updatedTransaction =
                    transactionRepository.save(transaction);

            return toResponse(updatedTransaction, items);
        }

        throw new BadRequestException(
                "Invalid transaction status transition"
        );
    }
}