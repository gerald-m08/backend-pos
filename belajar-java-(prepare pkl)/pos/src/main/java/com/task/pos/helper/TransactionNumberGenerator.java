package com.task.pos.helper;

import com.task.pos.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TransactionNumberGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int MAX_ATTEMPTS = 5;

    private final TransactionRepository transactionRepository;

    public String generate() {

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {

            String candidate = build();

            if (!transactionRepository.existsByTransactionNumber(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException(
                "Failed to generate a unique transaction number after " + MAX_ATTEMPTS + " attempts"
        );
    }

    private String build() {

        String datePart = LocalDate.now().format(DATE_FORMAT);
        int randomSuffix = ThreadLocalRandom.current().nextInt(0, 1_000_000);

        return "TRX-%s-%06d".formatted(datePart, randomSuffix);
    }
}