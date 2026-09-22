package com.task.pos.service;

import com.task.pos.dto.request.CreateTransactionRequest;
import com.task.pos.dto.request.TransactionItemRequest;
import com.task.pos.dto.response.TransactionHistoryResponse;
import com.task.pos.dto.response.TransactionResponse;
import com.task.pos.entity.Product;
import com.task.pos.entity.Transaction;
import com.task.pos.entity.TransactionItem;
import com.task.pos.entity.User;
import com.task.pos.enums.PaymentMethod;
import com.task.pos.enums.TransactionStatus;
import com.task.pos.exception.ConflictException;
import com.task.pos.exception.ResourceNotFoundException;
import com.task.pos.helper.SortFieldValidator;
import com.task.pos.helper.TransactionAccessGuard;
import com.task.pos.helper.TransactionNumberGenerator;
import com.task.pos.mapper.TransactionMapper;
import com.task.pos.repository.ProductRepository;
import com.task.pos.repository.TransactionRepository;
import com.task.pos.repository.UserRepository;
import com.task.pos.specification.TransactionSpecification;
import com.task.pos.helper.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "transactionNumber", "transactionDate", "totalAmount", "paymentMethod", "status"
    );

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockService stockService;
    private final TransactionMapper transactionMapper;
    private final TransactionNumberGenerator transactionNumberGenerator;
    private final TransactionAccessGuard transactionAccessGuard;
    private final SortFieldValidator sortFieldValidator;
    private final PageableFactory pageableFactory;

    // ==========================================
    // CREATE & PAYMENT
    // ==========================================

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        User currentUser = getCurrentUser();

        Transaction transaction = Transaction.builder()
                .transactionNumber(transactionNumberGenerator.generate())
                .processedBy(currentUser)
                .paymentMethod(request.getPaymentMethod())
                .status(TransactionStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .paymentAmount(BigDecimal.ZERO)
                .build();

        List<TransactionItem> transactionItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (TransactionItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            stockService.validateStock(product, itemRequest.getQuantity());

            BigDecimal unitPrice = product.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(subtotal);

            TransactionItem transactionItem = TransactionItem.builder()
                    .transaction(transaction)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            transactionItems.add(transactionItem);
        }

        transaction.setItems(transactionItems);
        transaction.setTotalAmount(totalAmount);

        resolvePayment(transaction, request.getPaymentMethod(), request.getPaymentAmount(), totalAmount);

        boolean isInstantPayment = request.getPaymentMethod() == PaymentMethod.CASH;
        transaction.setStatus(isInstantPayment ? TransactionStatus.PAID : TransactionStatus.PENDING);

        Transaction savedTransaction = transactionRepository.save(transaction);

       reduceStockForTransaction(savedTransaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponse confirmPayment(UUID id) {

        Transaction transaction = findTransactionOrThrow(id);

        transactionAccessGuard.assertCanAccess(transaction);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new ConflictException("Only pending transactions can be confirmed");
        }

        transaction.setStatus(TransactionStatus.PAID);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponse cancelTransaction(UUID id) {

        Transaction transaction = findTransactionOrThrow(id);

        transactionAccessGuard.assertCanAccess(transaction);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new ConflictException("Only pending transactions can be cancelled");
        }

        transaction.setStatus(TransactionStatus.CANCELLED);

        Transaction savedTransaction = transactionRepository.save(transaction);

        restoreStockForTransaction(savedTransaction);

        return transactionMapper.toResponse(savedTransaction);
    }


    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(UUID id) {

        Transaction transaction = findTransactionOrThrow(id);

        transactionAccessGuard.assertCanAccess(transaction);

        return transactionMapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public Page<TransactionHistoryResponse> getTransactions(
            String search,
            UUID processedById,
            PaymentMethod paymentMethod,
            TransactionStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int limit,
            String sortBy,
            String sortDirection
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UUID effectiveProcessedById = transactionAccessGuard.isAdmin(authentication)
                ? processedById : getCurrentUser().getId();

        String validatedSortBy = sortFieldValidator.validate(sortBy, ALLOWED_SORT_FIELDS);
        Pageable pageable = pageableFactory.build(page, limit, sortDirection, validatedSortBy);

        Specification<Transaction> specification = TransactionSpecification.search(
                search, effectiveProcessedById, paymentMethod, status, startDate, endDate
        );

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);

        return transactionPage.map(transactionMapper::toHistoryResponse);
    }


    //Private helper

    private User getCurrentUser() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Transaction findTransactionOrThrow(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }


    private void resolvePayment(
            Transaction transaction,
            PaymentMethod paymentMethod,
            BigDecimal requestedPaymentAmount,
            BigDecimal totalAmount
    ) {
        if (paymentMethod == PaymentMethod.CASH) {
            validatePayment(requestedPaymentAmount, totalAmount);
        } else {
            validatePaymentNonCash(requestedPaymentAmount, totalAmount);
        }

        transaction.setPaymentAmount(requestedPaymentAmount);
        transaction.setChangeAmount(requestedPaymentAmount.subtract(totalAmount));
    }

    private void validatePayment(BigDecimal paymentAmount, BigDecimal totalAmount) {

        if (paymentAmount.compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("Payment amount cannot be less than total amount");
        }
    }

    private void validatePaymentNonCash (BigDecimal paymentAmount, BigDecimal totalAmount) {
        if (paymentAmount.compareTo(totalAmount) != 0) {
            throw new  IllegalArgumentException ("Payment amount must equal than total amount");
        }
    }

    private void reduceStockForTransaction(Transaction transaction) {

        transaction.getItems().stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .forEach(item ->
                        stockService.decreaseStock(item.getProduct().getId(), item.getQuantity())
                );
    }

    private void restoreStockForTransaction (Transaction transaction) {

        transaction.getItems().stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .forEach ( item ->
                        stockService.restoreStock(item.getProduct().getId(), item.getQuantity())
                );
    }
}