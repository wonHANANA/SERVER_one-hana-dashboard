package com.onehana.onehanadashboard.crawling.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatedKeywordResponse {
    private String childKeyword;
    private Boolean isPos;
    private Double percentage;
    private Boolean isEsgKeyword;
    private Integer duplicateCnt;
    private Double sumKeywordWorth;
    private Integer newsCnt;
}
