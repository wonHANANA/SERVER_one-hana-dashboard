package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.service.GoogleTrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoogleTrendController {

    private final GoogleTrendService googleTrendService;

    @PostMapping("/google")
    public void saveGoogleTrend(@RequestBody List<GoogleTrend> googleTrend) {
        System.out.println("inini");
        googleTrendService.saveGoogleTrend(googleTrend);
    }
}
