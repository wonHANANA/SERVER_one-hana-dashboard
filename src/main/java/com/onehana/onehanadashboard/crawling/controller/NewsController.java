package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.service.NewsService;
import com.onehana.onehanadashboard.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "네이버 크롤링", description = "날짜 형식: yyyyMMdd (20230101)")
    @PostMapping("/naver")
    public String start(@RequestParam String keyword,
                        @RequestParam String startDate,
                        @RequestParam String endDate) {

        if (!StringUtil.isValidDateFormat(startDate) || !StringUtil.isValidDateFormat(endDate)) {
            throw new BaseException(BaseResponseStatus.INVALID_DATE_TYPE);
        }

        newsService.process(keyword, startDate, endDate);
        return "크롤링 시작";
    }

    @Operation(summary = "뉴스기사 키워드 1개로 조회", description = "뉴스기사 키워드 1개로 조회")
    @GetMapping("/keyword/{keyword}")
    public BaseResponse<List<News>> getNewsByKeyword(@PathVariable String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<News> news = newsService.getNewsByKeyword(keyword);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "뉴스기사 키워드 2개로 조회", description = "뉴스기사 키워드 2개로 조회")
    @GetMapping("/keyword/{keyword1}/{keyword2}")
    public BaseResponse<List<News>> getNewsByKeywords(@PathVariable String keyword1,
                                                      @PathVariable String keyword2) {
        if (StringUtils.isBlank(keyword1) || StringUtils.isBlank(keyword2)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<News> news = newsService.getNewsByKeywords(keyword1, keyword2);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "특정 기간 동안의 뉴스기사 조회", description = "날짜 형식: yyyyMMdd (20230101)")
    @GetMapping("/date/{startDate}/{endDate}")
    public BaseResponse<List<News>> getNewsByDate(@PathVariable String startDate,
                                                  @PathVariable String endDate) {

        if (!StringUtil.isValidDateFormat(startDate) || !StringUtil.isValidDateFormat(endDate)) {
            throw new BaseException(BaseResponseStatus.INVALID_DATE_TYPE);
        }

        List<News> news = newsService.getNewsByDate(startDate, endDate);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "키워드 포함 앞뒤 length 만큼 조회", description = "keyword: str, length: int")
    @GetMapping("/sentence/keyword/{keyword}/length/{length}")
    public BaseResponse<List<String>> getSentenceWithKeyword(@PathVariable String keyword,
                                                             @PathVariable int length) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<String> news = newsService.getCustomSentenceWithKeyword(keyword, length);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "키워드 포함 문장 단위 조회", description = "문장은 마침표(.) 단위로 잘랐음")
    @GetMapping("/sentence/keyword/{keyword}")
    public BaseResponse<List<String>> getDotSentenceWithKeyword(@PathVariable String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<String> news = newsService.getSentenceWithKeyword(keyword);
        return new BaseResponse<>(news);
    }
}
