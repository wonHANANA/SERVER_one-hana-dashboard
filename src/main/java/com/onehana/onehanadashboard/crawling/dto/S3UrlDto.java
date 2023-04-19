package com.onehana.onehanadashboard.crawling.dto;

import com.onehana.onehanadashboard.crawling.entity.S3Url;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class S3UrlDto {

    @NotBlank(message = "키워드는 필수 입력 항목입니다.")
    private String keyword;
    @NotBlank(message = "url은 필수 입력 항목입니다.")
    private String url;

    public S3Url toEntity() {
        return S3Url.builder()
                .keyword(keyword)
                .url(url)
                .build();
    }
}
