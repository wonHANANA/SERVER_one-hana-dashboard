package com.onehana.onehanadashboard.crawling.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoogleSearchResponse {
    private List<Long> actualSearched;
    private List<Long> predictSearched;
}
