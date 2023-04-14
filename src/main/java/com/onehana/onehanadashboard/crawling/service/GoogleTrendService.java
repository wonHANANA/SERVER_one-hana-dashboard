package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.repository.GoogleTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleTrendService {

    private final GoogleTrendRepository googleTrendRepository;

    public List<GoogleTrend> saveGoogleTrend(List<GoogleTrend> googleTrend) {
        return googleTrendRepository.saveAll(googleTrend);
    }
}