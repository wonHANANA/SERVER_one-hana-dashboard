package com.onehana.onehanadashboard.crawling.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GoogleSearchRequest {
    private String keyword;
    private Boolean isActual;
    private List<Long> searched;
}
