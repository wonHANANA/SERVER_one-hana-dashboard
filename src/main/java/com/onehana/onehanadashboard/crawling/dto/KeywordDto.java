package com.onehana.onehanadashboard.crawling.dto;

import com.onehana.onehanadashboard.crawling.entity.Keyword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;
    @NotNull(message = "긍-부정 여부는 필수 입력 항목입니다.")
    private Boolean isPos;
    @NotNull(message = "퍼센트는 필수 입력 항목입니다.")
    private Double percentage;
    @NotNull(message = "esg 여부는 필수 입력 항목입니다.")
    private Boolean isEsgKeyword;

    public Keyword toEntity() {
        return Keyword.builder()
                .name(name)
                .isPos(isPos)
                .percentage(percentage)
                .isEsgKeyword(isEsgKeyword)
                .build();
    }
}