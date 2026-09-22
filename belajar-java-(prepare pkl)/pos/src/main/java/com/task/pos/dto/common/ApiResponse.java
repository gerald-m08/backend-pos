package com.task.pos.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.pos.dto.response.PageResponse;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final PageResponse pagination;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.pagination = null;
    }

    public ApiResponse(boolean success, String message, T data, PageResponse pagination) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
}