package com.onehana.onehanadashboard.junitTest.repository;

import com.onehana.onehanadashboard.junitTest.entity.Esg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EsgRepository extends JpaRepository<Esg, Long> {

    Optional<Esg> findByKeyword(String keyword);
}
