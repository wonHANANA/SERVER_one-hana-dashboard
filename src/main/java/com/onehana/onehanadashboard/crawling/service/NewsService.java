package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        driver = new ChromeDriver(options);

        try {
            naver(keyword, startDate, endDate);

            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
    }

    public void naver(String keyword, String startDate, String endDate) throws InterruptedException {
        String title;
        String date;
        String text;

        int start_year = Integer.parseInt(startDate.substring(0, 4));
        int start_month = Integer.parseInt(startDate.substring(4, 6));
        int start_day = Integer.parseInt(startDate.substring(6, 8));

        int end_year = Integer.parseInt(endDate.substring(0, 4));
        int end_month = Integer.parseInt(endDate.substring(4, 6));
        int end_day = Integer.parseInt(endDate.substring(6, 8));

        String url = "https://naver.com";
        driver.get(url);

        driver.findElement(By.xpath("//*[@id=\"NM_FAVORITE\"]/div[1]/ul[2]/li[3]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys(keyword);

        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        LocalDate start = LocalDate.of(start_year, start_month, start_day); // 시작 날짜
        LocalDate end = LocalDate.of(end_year, end_month, end_day); // 종료 날짜

        // 시작 날짜부터 종료 날짜까지 하루씩 증가하면서 출력
        for (LocalDate searchDate = start; !searchDate.isAfter(end); searchDate = searchDate.plusDays(1)) {
            driver.findElement(By.xpath("//*[@id=\"schoption14\"]")).click();

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

            for (int i = 1; i <= 11; i++) {
                if (i == 3) i++;    // 페이지 2번으로 갈때부터 이전 탭이 생겨서 번호가 한칸씩 늘어난다

                try {
                    driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                } catch (NoSuchElementException e) {
                    break;
                }

                List<News> allNews = new ArrayList<>();
                for (int j = 1; j < 41; j++) {
                    try {
                        driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/div[2]/dl/dd[%d]/a", j))).click();

                        title = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > h3")).getText();
                        date = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > div > span")).getText();
                        text = driver.findElement(By.cssSelector("#content.articleCont")).getText();

                    } catch (NoSuchElementException | UnhandledAlertException e) {
                        continue;
                    }

                    text = text.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9. \\r\\n|\\r|\\n|\\n\\r]", "");

                    News news = News.builder()
                            .keyword(keyword)
                            .title(title)
                            .date(date)
                            .text(text)
                            .build();

                    allNews.add(news);
                    driver.navigate().back();
                }
                newsRepository.saveAll(allNews);
            }

            // 첫 페이지 이후
            boolean hasPage = true;
            while (hasPage) {
                for (int i = 3; i <= 13; i++) {
                    try {
                        driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                    } catch (NoSuchElementException e) {
                        hasPage = false;
                        break;
                    }
                    List<News> allNews = new ArrayList<>();

                    for (int j = 1; j < 41; j++) {
                        try {
                            driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/div[2]/dl/dd[%d]/a", j))).click();

                            title = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > h3")).getText();
                            date = driver.findElement(By.cssSelector("#contentarea_left > div.boardView.size4 > div.article_header > div.article_info > div > span")).getText();
                            text = driver.findElement(By.cssSelector("#content.articleCont")).getText();

                        } catch (NoSuchElementException | UnhandledAlertException e) {
                            continue;
                        }
                        text = text.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9. \\r\\n|\\r|\\n|\\n\\r]", "");

                        News news = News.builder()
                                .keyword(keyword)
                                .title(title)
                                .date(date)
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
        return newsRepository.findByTextContains(keyword);
    }

    public List<News> getNewsByKeywords(String keyword1, String keyword2) {
        return newsRepository.findByTextContains(keyword1, keyword2);
    }
}
