package com.onehana.onehanadashboard.crawling.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(length = 2000)
    private String title;
    private String date;
    @Column(length = 5000)
    private String text;
}
