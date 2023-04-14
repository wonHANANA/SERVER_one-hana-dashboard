package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private WebDriver driver;
    private WebElement web;

    public void process(String keyword, String startDate, String endDate) {
        System.setProperty("webdriver.chrome.driver", "/Users/idonghyun/IdeaProjects/hana/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("headless");
        driver = new ChromeDriver(options);

        try {
            naver(keyword, startDate, endDate);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        driver.close();
        driver.quit();
    }

    public void naver(String keyword, String startDate, String endDate) throws InterruptedException, ParseException {
        String title;
        String date;
        String text;

        String url = "https://naver.com";
        driver.get(url);

        driver.findElement(By.xpath("//*[@id=\"NM_FAVORITE\"]/div[1]/ul[2]/li[3]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys(keyword);

        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)),
                Integer.parseInt(startDate.substring(4, 6)),
                Integer.parseInt(startDate.substring(6, 8))); // 시작 날짜

        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)),
                Integer.parseInt(endDate.substring(4, 6)),
                Integer.parseInt(endDate.substring(6, 8))); // 종료 날짜

        // 시작 날짜부터 종료 날짜까지 하루씩 증가하면서 출력
        for (LocalDate searchDate = start; !searchDate.isAfter(end); searchDate = searchDate.plusDays(1)) {
            driver.findElement(By.xpath("//*[@id=\"schoption14\"]")).click();

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

            boolean hasPage = true; // 아래의 while문 조건
            for (int i = 1; i <= 11; i++) {
                if (i == 3) i++;    // 첫 페이지는 2번으로 간 뒤부터 1번 옆에 이전 탭이 생겨서 번호가 1만큼 늘어난다

                try {
                    driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                } catch (NoSuchElementException e) {
                    hasPage = false;    // 첫 페이지에서 10페이지를 넘지 못한다면 아래의 while문을 실행하지 못하게 한다
                    break;
                }

                List<News> allNews = new ArrayList<>();
                for (int j = 1; j <= 40; j++) {
                    try {
                        driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/div[2]/dl/dd[%d]/a", j))).click();

                        title = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > h3")).getText();
                        date = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > div > span")).getText();
                        text = driver.findElement(By.cssSelector("#content.articleCont")).getText();

                    } catch (NoSuchElementException | UnhandledAlertException e) {
                        continue;
                    }

                    text = text.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9. \\r\\n|\\r|\\n|\\n\\r]", "");

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, format);

                    News news = News.builder()
                            .searchKeyword(keyword)
                            .title(title)
                            .date(dateTime)
                            .text(text)
                            .build();

                    allNews.add(news);
                    driver.navigate().back();
                }
                newsRepository.saveAll(allNews);
            }

            // 첫 페이지 이후
            while (hasPage) {
                for (int i = 3; i <= 13; i++) {
                    try {
                        driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                    } catch (NoSuchElementException e) {
                        hasPage = false;
                        break;
                    }
                    List<News> allNews = new ArrayList<>();

                    for (int j = 1; j <= 40; j++) {
                        try {
                            driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/div[2]/dl/dd[%d]/a", j))).click();

                            title = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > h3")).getText();
                            date = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > div > span")).getText();
                            text = driver.findElement(By.cssSelector("#content.articleCont")).getText();

                        } catch (NoSuchElementException | UnhandledAlertException e) {
                            continue;
                        }
                        text = text.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9. \\r\\n|\\r|\\n|\\n\\r]", "");

                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime dateTime = LocalDateTime.parse(date, format);

                        News news = News.builder()
                                .searchKeyword(keyword)
                                .title(title)
                                .date(dateTime)
                                .text(text)
                                .build();

                        allNews.add(news);
                        driver.navigate().back();
                    }
                    newsRepository.saveAll(allNews);
                }
            }
        }
    }

    public List<News> getNewsByKeyword(String keyword) {
        return newsRepository.findByTextContainsIgnoreCase(keyword);
    }

    public List<News> getNewsByKeywords(String keyword1, String keyword2) {
        return newsRepository.findByTextContainsIgnoreCase(keyword1, keyword2);
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

    public static List<String> extractKeywordContext(String text, String keyword, int length) {
        List<Integer> indices = new ArrayList<>();

        int keywordLen = keyword.length();
        keyword = keyword.toLowerCase();

        int index = text.toLowerCase().indexOf(keyword);
        while (index >= 0) {
            indices.add(index);
            index = text.toLowerCase().indexOf(keyword, index + keywordLen);
        }

        List<String> result = new ArrayList<>();
        for (int i : indices) {
            int start = Math.max(0, i - length);
            int end = Math.min(text.length(), i + keywordLen + length);
            String context = text.substring(start, end);
            context = context.replaceAll("\n", "");
            result.add(context);
        }
        return result;
    }

    public static List<String> getSentenceContainingKeyword(String text, String keyword) {
        keyword = keyword.toLowerCase();

        List<String> result = new ArrayList<>();
        String[] sentences = text.split("[.]"); // . 단위로 문장 분리

        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyword)) {
                sentence = sentence.replaceAll("\n", "");
                result.add(sentence);
            }
        }
        return result;
    }
}
