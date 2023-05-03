package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDetailDto;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDto;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordModifyDetailDto;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordModifyDto;
import com.onehana.onehanadashboard.crawling.dto.response.RelatedKeywordResponse;
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

    @Transactional(readOnly = true)
    public List<RelatedKeywordResponse> getRelatedKeywords(String keyword) {
        Keyword findKeyword = keywordRepository.findByName(keyword)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

        List<RelatedKeyword> relatedKeywords = relatedKeywordRepository.findAllByKeywordIdOrderBy(findKeyword);

        List<RelatedKeywordResponse> responseList = new ArrayList<>();
        for (RelatedKeyword relatedKeyword : relatedKeywords) {
            RelatedKeywordResponse res = RelatedKeywordResponse.builder()
                    .childKeyword(relatedKeyword.getName())
                    .isPos(relatedKeyword.getIsPos())
                    .percentage(relatedKeyword.getPercentage())
                    .isEsgKeyword(relatedKeyword.getIsEsgKeyword())
                    .duplicateCnt(relatedKeyword.getDuplicateCnt())
                    .sumKeywordWorth(relatedKeyword.getSumKeywordWorth())
                    .newsCnt(relatedKeyword.getNewsCnt())
                    .build();

            responseList.add(res);
        }
        return responseList;
    }

    public RelatedKeywordModifyDto modifyRelatedKeywords(RelatedKeywordModifyDto request) {
        Keyword parentKeyword = keywordRepository.findByName(request.getParentKeyword())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

        List<RelatedKeywordModifyDetailDto> keywordDetails = request.getKeywordDetails();
        for (RelatedKeywordModifyDetailDto keywordDetail : keywordDetails) {
            RelatedKeyword childKeyword = relatedKeywordRepository.findByNameAndKeyword(keywordDetail.getChildKeyword(), parentKeyword)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND));

            childKeyword.setIsPos(keywordDetail.getIsPos());
            childKeyword.setPercentage(keywordDetail.getPercentage());
            relatedKeywordRepository.save(childKeyword);
        }
        return request;
    }
}
