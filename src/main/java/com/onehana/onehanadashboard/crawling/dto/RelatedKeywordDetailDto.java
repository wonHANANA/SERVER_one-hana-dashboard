package com.onehana.onehanadashboard.crawling.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedKeywordDetailDto {
    private Integer duplicateCnt;
    private Double sumKeywordWorth;
    private String childKeyword;
}
