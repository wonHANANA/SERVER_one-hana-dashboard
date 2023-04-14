package com.onehana.onehanadashboard.stringTest;

import com.onehana.onehanadashboard.crawling.entity.News;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StringTest {

    @Test
    @DisplayName("페이지 테스트")
    void crawlingPage() {
        int k = 0;

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j < 41; j++) {
                System.out.println(j);
            }
            System.out.println("첫 페이지");
            System.out.println(i + "번 페이지 클릭");
        }

        boolean isTrue = true;
        while (isTrue) {
            for (int i = 3; i <= 12; i++) {
                k++;
                for (int j = 1; j < 41; j++) {
                    System.out.println(j);
                }
                System.out.println("나머지 페이지");
                System.out.println(i + "번 페이지 클릭");

                if (k > 100)
                    isTrue = false;
            }
        }
    }

    @Test
    @DisplayName("날짜 바꾸기 테스트")
    void dateTest() {
        String startDate = "20231101";
        String endDate = "20231213";

        int start_year = Integer.parseInt(startDate.substring(0, 4));
        int start_month = Integer.parseInt(startDate.substring(4, 6));
        int start_day = Integer.parseInt(startDate.substring(6, 8));

        int end_year = Integer.parseInt(endDate.substring(0, 4));
        int end_month = Integer.parseInt(endDate.substring(4, 6));
        int end_day = Integer.parseInt(endDate.substring(6, 8));

        LocalDate start = LocalDate.of(start_year, start_month, start_day); // 시작 날짜
        LocalDate end = LocalDate.of(end_year, end_month, end_day); // 종료 날짜

        // 시작 날짜부터 종료 날짜까지 하루씩 증가하면서 출력
        for (LocalDate searchDate = start; !searchDate.isAfter(end); searchDate = searchDate.plusDays(1)) {
            System.out.println(searchDate);
        }
    }

    @Test
    @DisplayName("키워드 포함된 문자열 앞뒤 x글자만큼 추출하기 (문장 마다 분리")
    void keywordTest() {
        String text = "esg. 그것은 무엇인가 esg는 최고인가. 오늘은 어쩌고 저쩌고 그랬고 저랬고. 짜파게티는 맛있고 저랬고 파김치는 esg 화이팅.";
        String keyword = "esg";
        int contextSize = 20;
        List<String> result = getSentencesContainingKeyword(text, keyword, contextSize);
        System.out.println(result);
    }

    @Test
    @DisplayName("키워드 포함된 문자열 앞뒤 x글자만큼 추출하기 (통으로)")
    void keywordTest2() {
        String text = "esg. 그것은 무엇인가 esg는 최고인가. 오늘은 어쩌고 저쩌고 그랬고 저랬고. 짜파게티는 맛있고 저랬고 파김치는 esg 화이팅.";
        String keyword = "esg";

        List<String> contexts = extractKeywordContext(text, keyword);
        for (String context : contexts) {
            System.out.println(context);
        }
    }

    public static List<String> getSentencesContainingKeyword(String text, String keyword, int contextSize) {
        List<String> result = new ArrayList<>();
        String[] sentences = text.split("[.]"); // 문장 단위로 분리

        for (String sentence : sentences) {
            if (sentence.contains(keyword)) { // 키워드를 포함하는 문장인 경우
                int startIndex = Math.max(0, sentence.indexOf(keyword) - contextSize); // 키워드 시작 인덱스 이전부터 contextSize만큼 문자열을 추출
                int endIndex = Math.min(sentence.length(), sentence.indexOf(keyword) + keyword.length() + contextSize); // 키워드 끝 인덱스 이후부터 contextSize만큼 문자열을 추출
                result.add(sentence.substring(startIndex, endIndex));
            }
        }
        return result;
    }

    public static List<String> extractKeywordContext(String text, String keyword) {
        int keywordLen = keyword.length();
        List<Integer> indices = new ArrayList<>();
        int index = text.indexOf(keyword);
        while (index >= 0) {
            indices.add(index);
            index = text.indexOf(keyword, index + keywordLen);
        }
        List<String> result = new ArrayList<>();
        for (int i : indices) {
            int start = Math.max(0, i - 20);
            int end = Math.min(text.length(), i + keywordLen + 20);
            String context = text.substring(start, end);
            result.add(context);
        }
        return result;
    }
}
