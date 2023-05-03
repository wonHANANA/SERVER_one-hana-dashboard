package com.onehana.onehanadashboard.crawling.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RelatedKeywordModifyDto {
    @NotBlank
    private String parentKeyword;
    @Valid
    private List<RelatedKeywordModifyDetailDto> keywordDetails;
}
