package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private WebDriver driver;
    private WebElement web;

    public void process(String keyword, String period) {
        System.setProperty("webdriver.chrome.driver", "/Users/idonghyun/IdeaProjects/hana/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        try {
            naver(keyword, period);

            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
    }

    public void naver(String keyword, String period) throws InterruptedException {
        String title;
        String date;
        String text;

        String url = "https://naver.com";
        driver.get(url);

        driver.findElement(By.cssSelector("[data-clk = 'svc.stock']")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys(keyword);

        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        driver.findElement(By.xpath(String.format("//*[@id=\"schoption%s\"]", period))).click();

        driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

        for (int i = 1; i <= 10; i++) {
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
                        .isDuplicated(Boolean.TRUE)
                        .build();

                allNews.add(news);
                driver.navigate().back();
            }
            newsRepository.saveAll(allNews);

            i += 1;
            try {
                driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
            } catch (NoSuchElementException e) {
                break;
            }
        }   // 여기까지 리스트 1 ~ 10

        while (true) {
            for (int i = 3; i <= 12; i++) {
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
                            .isDuplicated(Boolean.TRUE)
                            .build();

                    allNews.add(news);
                    driver.navigate().back();
                }

                newsRepository.saveAll(allNews);

                i += 1;
                try {
                    driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                } catch (NoSuchElementException e) {
                    break;
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
