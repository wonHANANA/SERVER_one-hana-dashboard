package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.dto.response.KeywordResponse;
import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordDto addKeyword(KeywordDto keywordDto) {
        return keywordRepository.save(keywordDto.toEntity()).toDto();
    }

    @Transactional(readOnly = true)
    public List<KeywordDto> findEsgKeyword() {
        List<Keyword> keywords = keywordRepository.findAll();

        return keywords.stream()
                .map(KeywordDto::new)
                .collect(Collectors.toList());
    }
}
