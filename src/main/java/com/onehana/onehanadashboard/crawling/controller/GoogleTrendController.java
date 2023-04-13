package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.service.GoogleTrendService;
import io.swagger.v3.oas.annotations.Operation;
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
    public BaseResponse<List<GoogleTrend>> saveGoogleTrend(@RequestBody List<GoogleTrend> googleTrend) {
        List<GoogleTrend> savedTrends = googleTrendService.saveGoogleTrend(googleTrend);

        return new BaseResponse<>(savedTrends);
    }
    
}
