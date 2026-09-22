package com.example.pos.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {

    private int page;
    private int limit;
    private long totalData;
    private int totalPage;
    private List<T> data;
}