package com.onehana.onehanadashboard.crawling.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;
    private String date;

    @Column(length = 2000)
    private String title;

    @Column(length = 20000)
    private String text;

    @ColumnDefault("1")
    private Boolean isDuplicated;
}
