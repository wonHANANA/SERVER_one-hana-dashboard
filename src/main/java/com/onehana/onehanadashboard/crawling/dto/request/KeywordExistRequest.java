package com.onehana.onehanadashboard.crawling.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeywordExistRequest {
    private List<String> keywordList = new ArrayList<>();
}
