package com.onehana.onehanadashboard.crawling.controller;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponse;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.response.SNSResponse;
import com.onehana.onehanadashboard.crawling.service.SnsService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sns")
public class SnsController {

    private final SnsService snsService;

    @Operation(summary = "유튜버 댓글 크롤링", description = "유튜버 이름과 주제를 입력하면 동영상 목록 최신에 보이는 30개 정도의 동영상의 댓글을 저장한다")
    @PostMapping("/comments/{category}/{youtuber}")
    public BaseResponse<String> youtubeCrawling(@PathVariable String youtuber,
                                                @PathVariable String category) throws InterruptedException {

        if (StringUtils.isBlank(youtuber) || StringUtils.isBlank(category)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        snsService.YoutubeCrawling(youtuber, category);
        return new BaseResponse<>("크롤링이 완료되었습니다.");
    }

    @Operation(summary = "댓글 전체 출력", description = "유튜버 이름에 해당하는 모든 댓글 목록을 출력한다.")
    @GetMapping("/comments/{youtuber}")
    public BaseResponse<SNSResponse> showAllComments(@PathVariable String youtuber) {
        if (StringUtils.isBlank(youtuber)) {
            throw new BaseException(BaseResponseStatus.EMPTY_STRING);
        }

        SNSResponse res = snsService.getComments(youtuber);
        return new BaseResponse<>(res);
    }
}
