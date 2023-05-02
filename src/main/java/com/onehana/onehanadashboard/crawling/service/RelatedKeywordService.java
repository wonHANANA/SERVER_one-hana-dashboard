package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDetailDto;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDto;
import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.entity.RelatedKeyword;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import com.onehana.onehanadashboard.crawling.repository.RelatedKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<RelatedKeywordDetailDto> keywordDetails = relatedKeywordDto.getKeywordDetails();

        List<RelatedKeyword> relatedKeywords = new ArrayList<>();
        for (RelatedKeywordDetailDto detailDto : keywordDetails) {
            RelatedKeyword relatedKeyword = RelatedKeyword.builder()
                    .duplicateCnt(detailDto.getDuplicateCnt())
                    .sumKeywordWorth(detailDto.getSumKeywordWorth())
                    .keyword(keyword)
                    .name(detailDto.getChildKeyword())
                    .build();

            relatedKeywords.add(relatedKeyword);
        }
        relatedKeywordRepository.saveAll(relatedKeywords);

        return relatedKeywordDto;
    }

    public RelatedKeywordDto getRelatedKeywords(String keyword) {
        Keyword findKeyword = keywordRepository.findByName(keyword)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

        List<RelatedKeyword> relatedKeywords = relatedKeywordRepository.findAllByKeywordIdOrderBy(findKeyword);

        List<RelatedKeywordDetailDto> relatedKeywordDetailDtos = new ArrayList<>();
        for (RelatedKeyword relatedKeyword : relatedKeywords) {
            RelatedKeywordDetailDto relatedKeywordDetailDto = RelatedKeywordDetailDto.builder()
                    .childKeyword(relatedKeyword.getName())
                    .sumKeywordWorth(relatedKeyword.getSumKeywordWorth())
                    .duplicateCnt(relatedKeyword.getDuplicateCnt())
                    .build();

            relatedKeywordDetailDtos.add(relatedKeywordDetailDto);
        }

        RelatedKeywordDto relatedKeywordDto = RelatedKeywordDto.builder()
                .parentKeyword(findKeyword.getName())
                .keywordDetails(relatedKeywordDetailDtos)
                .build();

        return relatedKeywordDto;
    }
}
