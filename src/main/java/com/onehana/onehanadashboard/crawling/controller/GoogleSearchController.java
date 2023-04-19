package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.request.GoogleSearchRequest;
import com.onehana.onehanadashboard.crawling.dto.response.GoogleSearchResponse;
import com.onehana.onehanadashboard.crawling.entity.GoogleSearch;
import com.onehana.onehanadashboard.crawling.service.GoogleSearchService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/google/search")
public class GoogleSearchController {

    private final GoogleSearchService googleSearchService;

    @Operation(summary = "구글 검색량 저장", description = "구글 검색량을 배열로 받아 저장한다. 실제 값, 예측 값은 Boolean 형이다")
    @PostMapping
    public BaseResponse<List<GoogleSearch>> saveGoogleSearch(@RequestBody @Valid GoogleSearchRequest googleSearchRequest) {
        List<GoogleSearch> googleSearchList = googleSearchService.saveGoogleSearch(googleSearchRequest);

        return new BaseResponse<>(googleSearchList);
    }

    @Operation(summary = "구글 검색량 조회", description = "키워드에 해당하는 검색량을 실제 값, 예측 값 2종류로 반환한다")
    @GetMapping("/{keyword}")
    public BaseResponse<GoogleSearchResponse> getGoogleSearch(@PathVariable String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }
        GoogleSearchResponse res = googleSearchService.getGoogleSearch(keyword);

        return new BaseResponse<>(res);
    }
}
