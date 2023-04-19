package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.S3Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface S3UrlRepository extends JpaRepository<S3Url, Long> {

    Optional<S3Url> findByKeyword(String keyword);
}
