package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordDto;
import com.onehana.onehanadashboard.crawling.dto.RelatedKeywordModifyDto;
import com.onehana.onehanadashboard.crawling.dto.response.RelatedKeywordResponse;
import com.onehana.onehanadashboard.crawling.service.RelatedKeywordService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/related-keyword")
public class RelatedKeywordController {

    private final RelatedKeywordService relatedKeywordService;

    @Operation(summary = "파생 키워드 저장", description = "이름으로 이루어진 부모 키워드와 자식 키워드 배열을 넣고 저장한다.")
    @PostMapping
    public BaseResponse<RelatedKeywordDto> addRelatedKeyword(@RequestBody @Valid RelatedKeywordDto relatedKeywordDto) {
        RelatedKeywordDto res = relatedKeywordService.addRelatedKeyword(relatedKeywordDto);

        return new BaseResponse<>(res);
    }

    @Operation(summary = "파생 키워드 조회", description = "부모 키워드를 입력하여 해당하는 파생 키워드를 조회한다.")
    @GetMapping("/child")
    public BaseResponse<List<RelatedKeywordResponse>> getRelatedKeywords(@RequestParam String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }
        List<RelatedKeywordResponse> res = relatedKeywordService.getRelatedKeywords(keyword);

        return new BaseResponse<>(res);
    }

    @Operation(summary = "파생 키워드 긍정률 수정", description = "파생 키워드의 isPos와 percent를 수정한다.")
    @PutMapping
    public BaseResponse<RelatedKeywordModifyDto> modifyRelatedKeywords(@RequestBody @Valid RelatedKeywordModifyDto request) {
        RelatedKeywordModifyDto res = relatedKeywordService.modifyRelatedKeywords(request);

        return new BaseResponse<>(res);
    }
}
