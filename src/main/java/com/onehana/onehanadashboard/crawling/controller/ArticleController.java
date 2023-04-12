package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/crawling/naver")
    public String start(@RequestParam String keyword,
                      @RequestParam String period) {
        articleService.process(keyword, period);

        return "크롤링 시작";
    }

    @GetMapping("/news/{keyword}")
    public List<News> getNewsByKeyword(@PathVariable String keyword) {
        return articleService.getNewsByKeyword(keyword);
    }

    @GetMapping("/news/{keyword1}/{keyword2}")
    public List<News> getNewsByKeywords(@PathVariable String keyword1,
                                        @PathVariable String keyword2) {
        return articleService.getNewsByKeywords(keyword1, keyword2);
    }

//    @DeleteMapping("/news")
//    public String deleteDuplicateNews() {
//        int size = articleService.deleteDuplicateNews();
//        return "중복된 뉴스 기사의 수는 =" + size;
//    }
}
