package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    // 1. 키워드로 긁어오기 2. 키워드 2개로 긁어오기 3. 키워드가 포함된 문장의 앞뒤 3문장

    List<News> findByTextContains(String keyword);

    @Query(value = "select m from News m where m.text like %:keyword1% and m.text like %:keyword2%")
    List<News> findByTextContains(String keyword1, String keyword2);

//    @Transactional
//    @Modifying
//    @Query(value = "delete from News m where exists (select n from News n where m.title = n.title and m.id < n.id)")
//    int deleteDuplicateNews();
}


