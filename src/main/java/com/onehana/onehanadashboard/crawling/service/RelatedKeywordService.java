package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDto;
import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.entity.RelatedKeyword;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import com.onehana.onehanadashboard.crawling.repository.RelatedKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RelatedKeywordService {

    private final RelatedKeywordRepository relatedKeywordRepository;
    private final KeywordRepository keywordRepository;

    public RelatedKeywordDto addRelatedKeyword(RelatedKeywordDto relatedKeywordDto) {
        Keyword keyword = keywordRepository.findByName(relatedKeywordDto.getParentKeyword())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

        for (String childKeywordName : relatedKeywordDto.getChildKeyword()) {
            RelatedKeyword relatedKeyword = RelatedKeyword.builder()
                    .name(childKeywordName)
                    .keyword(keyword)
                    .build();

            relatedKeywordRepository.save(relatedKeyword);
        }
        return relatedKeywordDto;
    }

    public RelatedKeywordDto getRelatedKeywords(String keyword) {
        Keyword findKeyword = keywordRepository.findByName(keyword)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

        List<RelatedKeyword> relatedKeywords = relatedKeywordRepository.findAllByKeywordId(findKeyword.getId());
        List<String> relatedKeywordNames = relatedKeywords.stream()
                .map(RelatedKeyword::getName).toList();

        RelatedKeywordDto relatedKeywordDto = RelatedKeywordDto.builder()
                .parentKeyword(findKeyword.getName())
                .childKeyword(relatedKeywordNames)
                .build();

        return relatedKeywordDto;
    }
}
