package com.onehana.onehanadashboard.repository;

import com.onehana.onehanadashboard.crawling.controller.NewsController;
import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import com.onehana.onehanadashboard.junitTest.entity.Esg;
import com.onehana.onehanadashboard.junitTest.repository.EsgRepository;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private EsgRepository esgRepository;

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

    @Test
    @DisplayName("ESG keyword 삽입 테스트")
    void add_esg_keyword() {
        Esg esg = new Esg();
        esg.setKeyword("친환경");

        String myEsg = esgRepository.save(esg).getKeyword();
        String savedEsg = esgRepository.findById(1L).orElseThrow().getKeyword();

        assertThat(myEsg.length())
                .as("길이가 안맞네??")
                .isGreaterThan(0)
                .isLessThan(10);

        if (!Pattern.matches("^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\s]*$", myEsg)) {
            throw new AssertionFailure("특수문자가 있네??");
        }

        if (!Pattern.matches("^[|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|]*$", myEsg)) {
            throw new AssertionFailure("영어가 있네??");
        }

        assertThat(savedEsg)
                .as("내가 넣은 키워드가 아니네??")
                .isEqualTo("친환경");
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
