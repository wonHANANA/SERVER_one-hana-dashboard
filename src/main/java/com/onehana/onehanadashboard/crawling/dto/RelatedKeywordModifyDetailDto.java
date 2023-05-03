package com.onehana.onehanadashboard.crawling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedKeywordModifyDetailDto {
    @NotNull
    @NotBlank
    private String childKeyword;
    @NotNull
    private Boolean isPos;
    @NotNull
    private Double percentage;
}
