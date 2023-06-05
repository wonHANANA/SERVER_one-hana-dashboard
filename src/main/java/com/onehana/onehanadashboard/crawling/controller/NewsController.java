package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.response.RelatedKeywordResponse;
import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.service.NewsCrawlingService;
import com.onehana.onehanadashboard.crawling.service.NewsService;
import com.onehana.onehanadashboard.crawling.service.RelatedKeywordService;
import com.onehana.onehanadashboard.util.CustomStringUtil;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final NewsCrawlingService newsCrawlingService;
    private final RelatedKeywordService relatedKeywordService;

    @Operation(summary = "네이버 크롤링", description = "날짜 형식: yyyyMMdd (20230101)")
    @CrossOrigin(originPatterns = "http://localhost:3000")
    @PostMapping("/naver")
    public BaseResponse<String> startCrawling(@RequestParam String keyword,
                                              @RequestParam String startDate,
                                              @RequestParam String endDate,
                                              @RequestParam int quantity) {

        if (!CustomStringUtil.isValidDateFormat(startDate) || !CustomStringUtil.isValidDateFormat(endDate)) {
            throw new BaseException(BaseResponseStatus.INVALID_DATE_TYPE);
        }

        newsCrawlingService.naver(keyword, startDate, endDate, quantity);
        return new BaseResponse<>("크롤링 요청 성공") ;
    }

    @Operation(summary = "심플 네이버 크롤링", description = "입력 키워드에 대한 파생 키워드들의 최신 뉴스기사를 입력한 숫자만큼 가져온다.")
    @PostMapping("/simple-naver")
    public BaseResponse<String> startSimpleCrawling(@RequestParam String keyword,
                                                    @RequestParam int quantity) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<RelatedKeywordResponse> resList = relatedKeywordService.getRelatedKeywordsByParents(keyword);
        List<String> relatedKeywords = resList.stream()
                .map(RelatedKeywordResponse::getChildKeyword).collect(Collectors.toList());

        newsCrawlingService.simpleNaverCrawling(relatedKeywords, quantity);
        return new BaseResponse<>("크롤링 요청 성공");
    }

    @Operation(summary = "뉴스기사 본문 키워드 1개로 조회", description = "뉴스기사 본문에 있는 키워드 1개로 조회")
    @GetMapping("/keyword/{keyword}")
    public BaseResponse<List<News>> getNewsByKeyword(@PathVariable String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<News> news = newsService.getNewsByKeyword(keyword);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "뉴스기사 본문 키워드 2개로 조회", description = "뉴스기사 본문에 있는 키워드 2개로 조회")
    @GetMapping("/keyword/{keyword1}/{keyword2}")
    public BaseResponse<List<News>> getNewsByKeywords(@PathVariable String keyword1,
                                                      @PathVariable String keyword2) {
        if (StringUtils.isBlank(keyword1) || StringUtils.isBlank(keyword2)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<News> news = newsService.getNewsByKeywords(keyword1, keyword2);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "뉴스기사 검색 키워드로 조회", description = "검색 키워드에 해당하는 뉴스기사 전체 조회")
    @GetMapping("/search-keyword/{search-keyword}")
    public BaseResponse<List<News>> getNewsBySearchKeyword(@PathVariable("search-keyword") String searchKeyword) {
        if (StringUtils.isBlank(searchKeyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        List<News> news = newsService.getNewsBySearchKeyword(searchKeyword);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "특정 기간 동안의 뉴스기사 조회", description = "날짜 형식: yyyyMMdd (20230101)")
    @GetMapping("/date/{startDate}/{endDate}")
    public BaseResponse<List<News>> getNewsByDate(@PathVariable String startDate,
                                                  @PathVariable String endDate) {

        if (!CustomStringUtil.isValidDateFormat(startDate) || !CustomStringUtil.isValidDateFormat(endDate)) {
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
