package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.entity.RelatedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByName(String name);
}
