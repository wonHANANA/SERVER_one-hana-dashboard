package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "네이버 크롤링", description = "전체기간: 11, 한달: 12, 일주일: 13")
    @PostMapping("/naver")
    public String start(@RequestParam String keyword,
                        @RequestParam String period) {
        newsService.process(keyword, period);
        return "크롤링 시작";
    }

    @Operation(summary = "뉴스기사 키워드 1개로 조회", description = "뉴스기사 키워드 1개로 조회")
    @GetMapping("/{keyword}")
    public BaseResponse<List<News>> getNewsByKeyword(@PathVariable String keyword) {
        List<News> news = newsService.getNewsByKeyword(keyword);
        return new BaseResponse<>(news);
    }

    @Operation(summary = "뉴스기사 키워드 2개로 조회", description = "뉴스기사 키워드 2개로 조회")
    @GetMapping("/{keyword1}/{keyword2}")
    public BaseResponse<List<News>> getNewsByKeywords(@PathVariable String keyword1,
                                                      @PathVariable String keyword2) {
        List<News> news = newsService.getNewsByKeywords(keyword1, keyword2);
        return new BaseResponse<>(news);
    }
}
