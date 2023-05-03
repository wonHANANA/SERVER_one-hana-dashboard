package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.entity.RelatedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RelatedKeywordRepository extends JpaRepository<RelatedKeyword, Long> {
    Optional<RelatedKeyword> findByNameAndKeyword(String name, Keyword keyword_id);

    @Query("SELECT e FROM RelatedKeyword e WHERE e.keyword = :keyword_id ORDER BY e.duplicateCnt DESC, e.sumKeywordWorth DESC")
    List<RelatedKeyword> findAllByKeywordIdOrderBy(@Param("keyword_id") Keyword keyword_id);
}
