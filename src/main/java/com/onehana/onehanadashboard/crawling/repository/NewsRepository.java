package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByTextContainsIgnoreCase(String keyword);

    @Query(value = "select m from News m where m.text like %:keyword1% and m.text like %:keyword2%")
    List<News> findByTextContainsIgnoreCase(String keyword1, String keyword2);

    List<News> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
}


