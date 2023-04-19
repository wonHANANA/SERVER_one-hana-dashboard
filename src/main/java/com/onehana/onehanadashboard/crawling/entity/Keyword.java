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
    private Boolean isEsgKeyword;
    @Setter private Integer newsCnt;

    @PrePersist
    public void prePersist() {
        this.isEsgKeyword = this.isEsgKeyword == null ? Boolean.FALSE : this.isEsgKeyword;
    }

    public KeywordDto toDto() {
        return KeywordDto.builder()
                .name(name)
                .isPos(isPos)
                .percentage(percentage)
                .isEsgKeyword(isEsgKeyword)
                .build();
    }
}
