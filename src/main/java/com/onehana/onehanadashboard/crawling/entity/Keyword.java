package com.onehana.onehanadashboard.crawling.entity;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Keyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean isPos;
    private Double percentage;

    public KeywordDto toDto() {
        return KeywordDto.builder()
                .name(name)
                .isPos(isPos)
                .percentage(percentage)
                .build();
    }
}
