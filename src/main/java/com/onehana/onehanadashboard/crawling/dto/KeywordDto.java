package com.onehana.onehanadashboard.crawling.dto;

import com.onehana.onehanadashboard.crawling.entity.Keyword;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordDto {
    private String name;
    private Boolean isPos;
    private Double percentage;

    public Keyword toEntity() {
        return Keyword.builder()
                .name(name)
                .isPos(isPos)
                .percentage(percentage)
                .build();
    }
}