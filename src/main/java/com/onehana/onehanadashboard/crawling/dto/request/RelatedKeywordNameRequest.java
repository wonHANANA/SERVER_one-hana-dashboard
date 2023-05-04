package com.onehana.onehanadashboard.crawling.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RelatedKeywordNameRequest {
    private List<@NotBlank String> keywords;
}
