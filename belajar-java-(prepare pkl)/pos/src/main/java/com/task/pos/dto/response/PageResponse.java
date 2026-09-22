package com.task.pos.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageResponse {

    private final int page;
    private final int limit;
    private final long totalElements;
    private final int totalPages;

    public PageResponse(int page, int limit, long totalElements, int totalPages) {
        this.page = page;
        this.limit = limit;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}