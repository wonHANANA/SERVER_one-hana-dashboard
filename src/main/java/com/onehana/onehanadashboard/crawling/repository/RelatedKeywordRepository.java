package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.RelatedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatedKeywordRepository extends JpaRepository<RelatedKeyword, Long> {
    List<RelatedKeyword> findAllByKeywordId(Long keyword_id);
}
