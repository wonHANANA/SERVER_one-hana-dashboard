package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordService keywordService;

    @Operation(summary = "키워드 저장", description = "키워드의 이름, 긍-부정 여부 및 퍼센트")
    @PostMapping("")
    public BaseResponse<KeywordDto> addKeyword(@RequestBody KeywordDto keywordDto) {
        KeywordDto keyword = keywordService.addKeyword(keywordDto);

        return new BaseResponse<>(keyword);
    }
}
