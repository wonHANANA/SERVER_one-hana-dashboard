package com.onehana.onehanadashboard.crawling.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GoogleTrend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "날짜는 필수 입력 항목입니다.")
    private LocalDateTime date;
    @NotBlank(message = "키워드는 필수 입력 항목입니다.")
    private String keyword;
    @NotBlank(message = "순위는 필수 입력 항목입니다.")
    private Integer ranking;
    @NotBlank(message = "검색량은 필수 입력 항목입니다.")
    private Long searched;
}
