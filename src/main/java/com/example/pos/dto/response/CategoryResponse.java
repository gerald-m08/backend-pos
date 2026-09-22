package com.example.pos.dto.response;

import com.example.pos.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CategoryResponse {

    private UUID id;

    private String name;

    private String description;

    private CategoryStatus status;
}