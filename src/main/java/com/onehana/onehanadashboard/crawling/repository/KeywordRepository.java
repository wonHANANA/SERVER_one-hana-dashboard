package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("select k.name from Keyword k where k.isEsgKeyword = true")
    List<String> findNameByIsEsg();

    @Query("select k.name from Keyword k where k.isEsgKeyword = false")
    List<String> findNameByIsNotEsg();
}
