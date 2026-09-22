package com.task.pos.helper;

import com.task.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSkuGenerator {

    private final ProductRepository productRepository;

    public String generate() {
        Long nextValue = productRepository.getNextSkuSequenceValue();
        return "P-%03d".formatted(nextValue);
    }
}