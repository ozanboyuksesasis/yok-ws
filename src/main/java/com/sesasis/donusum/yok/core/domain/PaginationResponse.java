package com.sesasis.donusum.yok.core.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaginationResponse {
    private Object data;
    private long currentPageNumber;
    private long totalPageCount;
    private long totalCount;
}
