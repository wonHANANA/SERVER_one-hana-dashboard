package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/naver")
    public String start(@RequestParam String keyword,
                        @RequestParam String period) {
        newsService.process(keyword, period);

        return "크롤링 시작";
    }

    @GetMapping("/{keyword}")
    public List<News> getNewsByKeyword(@PathVariable String keyword) {
        return newsService.getNewsByKeyword(keyword);
    }

    @GetMapping("/{keyword1}/{keyword2}")
    public List<News> getNewsByKeywords(@PathVariable String keyword1,
                                        @PathVariable String keyword2) {
        return newsService.getNewsByKeywords(keyword1, keyword2);
    }
}
