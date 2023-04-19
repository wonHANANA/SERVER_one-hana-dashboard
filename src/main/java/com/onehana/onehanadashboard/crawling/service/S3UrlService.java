package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.S3UrlDto;
import com.onehana.onehanadashboard.crawling.entity.S3Url;
import com.onehana.onehanadashboard.crawling.repository.S3UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class S3UrlService {

    private final S3UrlRepository s3UrlRepository;

    public S3UrlDto saveUrl(S3UrlDto s3UrlDto) {
        return s3UrlRepository.save(s3UrlDto.toEntity()).toDto();
    }

    @Transactional(readOnly = true)
    public String getUrl(String keyword) {
        return s3UrlRepository.findByKeyword(keyword)
                .map(S3Url::getUrl)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));
    }
}
