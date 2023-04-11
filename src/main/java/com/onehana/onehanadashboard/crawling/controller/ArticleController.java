package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.crawling.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/")
    public void start(@RequestParam String keyword,
                      @RequestParam String period) {
        articleService.process(keyword, period);
    }
}
