package com.onehana.onehanadashboard.crawling.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "related_keyword",
        uniqueConstraints = @UniqueConstraint(columnNames = {"keyword_id", "name"}))
public class RelatedKeyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Setter private Boolean isPos;
    @Setter private Double percentage;
    private Boolean isEsgKeyword;
    private Integer duplicateCnt;
    private Double sumKeywordWorth;
    @Setter private Integer newsCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @PrePersist
    public void prePersist() {
        this.isEsgKeyword = this.isEsgKeyword == null ? Boolean.FALSE : this.isEsgKeyword;
        this.isPos = this.isPos == null ? Boolean.FALSE : this.isPos;
        this.percentage = this.percentage == null ? 0 : this.percentage;
        this.newsCnt = this.newsCnt == null ? 0 : this.newsCnt;
    }
}
