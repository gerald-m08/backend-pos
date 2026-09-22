package com.example.pos.service;

import com.example.pos.dto.request.PaymentRequest;
import com.example.pos.dto.response.TransactionItemResponse;
import com.example.pos.dto.response.TransactionResponse;
import com.example.pos.entity.Transaction;
import com.example.pos.entity.TransactionItem;
import com.example.pos.entity.User;
import com.example.pos.enums.PaymentMethod;
import com.example.pos.enums.TransactionStatus;
import com.example.pos.exception.BadRequestException;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.TransactionItemRepository;
import com.example.pos.repository.TransactionRepository;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public TransactionResponse pay(PaymentRequest request) {

        Transaction transaction =
                transactionRepository.findById(request.getTransactionId())
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
                    "You are not allowed to pay this transaction"
            );
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new BadRequestException(
                    "Only PENDING transaction can be paid"
            );
        }

        if (transaction.getPaymentMethod() == PaymentMethod.CASH) {
            throw new BadRequestException(
                    "Cash transaction is already paid"
            );
        }

        transaction.setStatus(TransactionStatus.PAID);

        Transaction updatedTransaction =
                transactionRepository.save(transaction);

        List<TransactionItem> items =
                transactionItemRepository.findByTransactionId(
                        transaction.getId()
                );

        return toResponse(updatedTransaction, items);
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

    private TransactionItemResponse toItemResponse(
            TransactionItem item
    ) {

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
}