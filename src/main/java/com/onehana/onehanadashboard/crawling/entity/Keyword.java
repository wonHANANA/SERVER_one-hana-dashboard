package com.onehana.onehanadashboard.crawling.entity;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Keyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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
