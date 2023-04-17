package com.onehana.onehanadashboard.crawling.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GoogleSearchRequest {
    @NotBlank(message = "키워드는 필수 입력 항목입니다.")
    private String keyword;
    @NotNull(message = "실제값 여부는 필수 입력 항목입니다.")
    private Boolean isActual;
    @NotNull(message = "검색량은 필수 입력 항목입니다.")
    private List<Long> searched;
}
