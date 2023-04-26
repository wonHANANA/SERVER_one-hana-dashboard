package com.onehana.onehanadashboard.crawling.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KeywordExistResponse {
    private List<String> exist;
    private List<String> notExist;
}
