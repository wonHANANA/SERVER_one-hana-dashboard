package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.dto.request.GoogleSearchRequest;
import com.onehana.onehanadashboard.crawling.dto.response.GoogleSearchResponse;
import com.onehana.onehanadashboard.crawling.entity.GoogleSearch;
import com.onehana.onehanadashboard.crawling.repository.GoogleSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoogleSearchService {

    private final GoogleSearchRepository googleSearchRepository;

    public List<GoogleSearch> saveGoogleSearch(GoogleSearchRequest googleSearchRequest) {
        List<Long> searched = googleSearchRequest.getSearched();

        List<GoogleSearch> googleSearchList = new ArrayList<>();
        for (Long searchValue : searched) {
            GoogleSearch googleSearch = GoogleSearch.builder()
                    .keyword(googleSearchRequest.getKeyword())
                    .isActual(googleSearchRequest.getIsActual())
                    .searched(searchValue)
                    .build();

            googleSearchList.add(googleSearch);
        }
        return googleSearchRepository.saveAll(googleSearchList);
    }

    @Transactional(readOnly = true)
    public GoogleSearchResponse getGoogleSearch(String keyword) {
        List<Long> actual = googleSearchRepository.findSearchedByKeywordAndIsActual(keyword);
        List<Long> predict = googleSearchRepository.findSearchedByKeywordAndIsPredict(keyword);

        GoogleSearchResponse response = GoogleSearchResponse.builder()
                .actualSearched(actual)
                .predictSearched(predict)
                .build();

        return response;
    }
}
