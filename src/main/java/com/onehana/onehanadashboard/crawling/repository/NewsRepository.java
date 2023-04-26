package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByTextContainsIgnoreCase(String keyword);

    @Query(value = "select m from News m where m.text like %:keyword1% and m.text like %:keyword2%")
    List<News> findByTextContainsIgnoreCase(@Param("keyword1") String keyword1,
                                            @Param("keyword2") String keyword2);

    List<News> findAllByDateBetween(LocalDateTime start, LocalDateTime end);


    @Query(value = "select m from News m where replace(m.searchKeyword, ' ', '') like replace (:search_keyword, ' ', '')")
    List<News> findAllBySearchKeyword(@Param("search_keyword") String search_keyword);
}


