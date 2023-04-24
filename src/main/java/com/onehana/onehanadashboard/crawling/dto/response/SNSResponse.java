package com.onehana.onehanadashboard.crawling.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SNSResponse {
    private String keyman;
    private String category;
    private List<String> text;
}
