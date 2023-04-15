package com.onehana.onehanadashboard.repository;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    @DisplayName("뉴스기사에서 키워드가 포함된 문장 앞뒤로 X 글자만큼 가져오기")
    void getSentenceWithKeyword() {
        String keyword = "MZ";

        List<News> newsList = newsRepository.findByTextContainsIgnoreCase("MZ");

        for (News news : newsList) {
            List<String> contexts = extractKeywordContext(news.getText(), keyword, 15);
            for (String context : contexts) {
                System.out.println(context);
            }
        }
    }

    @Test
    @DisplayName("두 날짜 사이의 뉴스 기사 조회")
    void getNewsInPeriod() {
        List<News> news = newsRepository.findAllByDateBetween(
                LocalDateTime.of(2023, 4, 10, 0, 0, 0),
                LocalDateTime.of(2023, 4, 10, 23, 59, 59));

        for (News news1 : news) {
            System.out.println(news1.getTitle());
        }
    }

    public static List<String> extractKeywordContext(String text, String keyword, int length) {
        int keywordLen = keyword.length();
        List<Integer> indices = new ArrayList<>();
        int index = text.indexOf(keyword);

        while (index >= 0) {
            indices.add(index);
            index = text.indexOf(keyword, index + keywordLen);
        }

        List<String> result = new ArrayList<>();
        for (int i : indices) {
            int start = Math.max(0, i - length);
            int end = Math.min(text.length(), i + keywordLen + length);
            String context = text.substring(start, end);
            result.add(context);
        }
        return result;
    }
}
