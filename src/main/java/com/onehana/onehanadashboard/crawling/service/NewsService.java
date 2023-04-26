package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.onehana.onehanadashboard.util.CustomStringUtil.extractKeywordContext;
import static com.onehana.onehanadashboard.util.CustomStringUtil.getSentenceContainingKeyword;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> getNewsByKeyword(String keyword) {
        return newsRepository.findByTextContainsIgnoreCase(keyword);
    }

    public List<News> getNewsByKeywords(String keyword1, String keyword2) {
        return newsRepository.findByTextContainsIgnoreCase(keyword1, keyword2);
    }

    public List<News> getNewsBySearchKeyword(String search_keyword) {
        return newsRepository.findAllBySearchKeyword(search_keyword);
    }

    public List<News> getNewsByDate(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDateTime startDate = LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(end, formatter).atTime(LocalTime.MAX);

        return newsRepository.findAllByDateBetween(startDate, endDate);
    }

    public List<String> getSentenceWithKeyword(String keyword) {
        List<News> newsList = newsRepository.findByTextContainsIgnoreCase(keyword);
        List<String> sentenceList = new ArrayList<>();

        for (News news : newsList) {
            List<String> contexts = getSentenceContainingKeyword(news.getText(), keyword);
            sentenceList.addAll(contexts);
        }
        return sentenceList;
    }

    public List<String> getCustomSentenceWithKeyword(String keyword, int length) {
        List<News> newsList = newsRepository.findByTextContainsIgnoreCase(keyword);
        List<String> sentenceList = new ArrayList<>();

        for (News news : newsList) {
            List<String> contexts = extractKeywordContext(news.getText(), keyword, length);
            sentenceList.addAll(contexts);
        }
        return sentenceList;
    }
}

