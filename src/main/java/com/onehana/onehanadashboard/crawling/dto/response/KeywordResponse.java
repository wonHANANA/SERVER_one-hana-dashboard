package com.onehana.onehanadashboard.crawling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KeywordResponse {
    private List<String> esg;
    private List<String> trend;
}
