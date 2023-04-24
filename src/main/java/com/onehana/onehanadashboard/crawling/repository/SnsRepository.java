package com.onehana.onehanadashboard.crawling.repository;

import com.onehana.onehanadashboard.crawling.entity.SNS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnsRepository extends JpaRepository<SNS, Long> {

    List<SNS> findAllByKeyman(String keyman);
}
