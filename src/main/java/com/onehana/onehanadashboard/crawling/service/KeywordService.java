package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.dto.response.KeywordResponse;
import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordDto addKeyword(KeywordDto keywordDto) {
        return keywordRepository.save(keywordDto.toEntity()).toDto();
    }

    public KeywordResponse findEsgKeyword() {
        List<String> esg = keywordRepository.findNameByIsEsg();
        List<String> trend = keywordRepository.findNameByIsNotEsg();

        return new KeywordResponse(esg, trend);
    }
}
