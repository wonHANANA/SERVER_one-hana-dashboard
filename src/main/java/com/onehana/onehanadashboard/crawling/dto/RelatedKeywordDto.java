package com.onehana.onehanadashboard.crawling.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedKeywordDto {
    @NotBlank
    private String parentKeyword;
    @Valid
    private List<RelatedKeywordDetailDto> keywordDetails;
}
