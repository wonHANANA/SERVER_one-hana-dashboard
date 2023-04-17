package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.entity.GoogleKeywordTrend;
import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.service.GoogleTrendService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleTrendController {

    private final GoogleTrendService googleTrendService;

    @Operation(summary = "구글 트렌드 저장", description = "구글 트렌드를 배열로 받아 저장한다")
    @PostMapping("/trend")
    public BaseResponse<List<GoogleTrend>> saveGoogleTrend(@RequestBody @Valid List<GoogleTrend> googleTrend) {
        List<GoogleTrend> savedTrends = googleTrendService.saveGoogleTrend(googleTrend);

        return new BaseResponse<>(savedTrends);
    }

    @Operation(summary = "[키워드 1개] 5년간 검색 추이 반환", description = "하나의 키워드를 검색할 시 5년간 추이 반환")
    @GetMapping("/trend/data")
//    public BaseResponse<List<GoogleKeywordTrend>> oneKeywordFastFiveGoogleTrend(@RequestParam String keyword) {
    public String oneKeywordFastFiveGoogleTrend(@RequestParam String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_VALUE);
        }

        List<GoogleKeywordTrend> googleKeywordTrendList = null;
        try {
            googleTrendService.process(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Working...";
    }
}
