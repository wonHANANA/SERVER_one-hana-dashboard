package com.onehana.onehanadashboard.crawling.entity;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.dto.S3UrlDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class S3Url {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;
    private String url;

    public S3UrlDto toDto() {
        return S3UrlDto.builder()
                .keyword(keyword)
                .url(url)
                .build();
    }
}
