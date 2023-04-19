package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordService keywordService;

    @Operation(summary = "키워드 저장", description = "키워드의 이름, 긍-부정 여부, 긍-부정도, esg 여부")
    @PostMapping
    public BaseResponse<KeywordDto> addKeyword(@RequestBody @Valid KeywordDto keywordDto) {
        KeywordDto keyword = keywordService.addKeyword(keywordDto);

        return new BaseResponse<>(keyword);
    }

    @Operation(summary = "키워드 전체 조회", description = "키워드를 리스트로 모두 출력한다")
    @GetMapping
    public BaseResponse<List<KeywordDto>> getAllKeywords() {
        List<KeywordDto> res = keywordService.findEsgKeyword();

        return new BaseResponse<>(res);
    }
}
