package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.dto.request.KeywordExistRequest;
import com.onehana.onehanadashboard.crawling.dto.response.KeywordExistResponse;
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

    @Operation(summary = "키워드에 해당하는 뉴스기사 수 크롤링", description = "키워드 테이블에 있는 키워드 전체의 네이버 뉴스기사 수를 구한다")
    @PostMapping("/news-cnt")
    public BaseResponse<String> countNews() {
        keywordService.countNewsWithKeyword();
        return new BaseResponse<>("모든 키워드의 뉴스기사 수 검색이 완료되었습니다.");
    }

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

    @Operation(summary = "키워드 DB 존재 유무 확인", description = "키워드 리스트를 받아 해당 키워드의 현재 DB 존재 여부를 출력한다.")
    @PostMapping("/is-exist")
    public BaseResponse<KeywordExistResponse> getExistKeyword(@RequestBody KeywordExistRequest keywordExistRequest) {
        KeywordExistResponse res = keywordService.isExistKeyword(keywordExistRequest);
        return new BaseResponse<>(res);
    }
}
