package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.GoogleSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoogleSearchRepository extends JpaRepository<GoogleSearch, Long> {

    @Query("SELECT gs.searched FROM GoogleSearch gs WHERE gs.keyword = :keyword AND gs.isActual = true")
    List<Long> findSearchedByKeywordAndIsActual(@Param("keyword") String keyword);

    @Query("SELECT gs.searched FROM GoogleSearch gs WHERE gs.keyword = :keyword AND gs.isActual = false")
    List<Long> findSearchedByKeywordAndIsPredict(@Param("keyword") String keyword);
}
