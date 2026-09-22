package com.task.pos.helper;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SortFieldValidator {

    public String validate(String sortBy, Set<String> allowedFields) {

        if (sortBy == null || !allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        return sortBy;
    }
}