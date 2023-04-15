package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordDto addKeyword(KeywordDto keywordDto) {
        return keywordRepository.save(keywordDto.toEntity()).toDto();
    }
}
