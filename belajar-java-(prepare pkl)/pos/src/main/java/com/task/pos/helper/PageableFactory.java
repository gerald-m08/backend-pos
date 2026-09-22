package com.task.pos.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    public Pageable build(int page, int size, String direction, String sortBy) {

        int normalizedPage = Math.max(page - 1, 0);
        int normalizedSize = normalizeSize(size);

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);

        return PageRequest.of(normalizedPage, normalizedSize, Sort.by(sortDirection, sortBy));
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return DEFAULT_SIZE;
        }
        return Math.min(size, MAX_SIZE);
    }
}