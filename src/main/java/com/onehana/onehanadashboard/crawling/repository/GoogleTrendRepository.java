package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleTrendRepository extends JpaRepository<GoogleTrend, Long> {
}
