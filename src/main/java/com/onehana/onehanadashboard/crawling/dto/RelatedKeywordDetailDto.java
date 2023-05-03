package com.onehana.onehanadashboard.crawling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedKeywordDetailDto {
    @NotNull
    private Integer duplicateCnt;
    @NotNull
    private Double sumKeywordWorth;
    @NotNull
    @NotBlank
    private String childKeyword;
}
