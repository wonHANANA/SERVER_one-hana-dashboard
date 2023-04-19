package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.S3UrlDto;
import com.onehana.onehanadashboard.crawling.service.S3UrlService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3UrlController {

    private final S3UrlService s3UrlService;

    @Operation(summary = "s3 url 저장", description = "키워드와 url을 입력해서 s3의 저장 url을 저장한다")
    @PostMapping
    public BaseResponse<S3UrlDto> saveUrl(@RequestBody @Valid S3UrlDto s3UrlDto) {
        S3UrlDto res = s3UrlService.saveUrl(s3UrlDto);
        return new BaseResponse<>(res);
    }

    @Operation(summary = "s3 url 단건 조회", description = "키워드를 통해 s3 url을 출력한다")
    @GetMapping
    public BaseResponse<String> getUrlByKeyword(@RequestParam String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        String res = s3UrlService.getUrl(keyword);
        return new BaseResponse<>(res);
    }
}
