package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.News;
import com.onehana.onehanadashboard.crawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsCrawlingService {

    private final NewsRepository newsRepository;
    private WebDriver driver;
    private String title;
    private String date;
    private String text;

    public void seleniumSetting() {
        String os = System.getProperty("os.name").toLowerCase();
        String currentDir = new File("").getAbsolutePath();
        System.out.println("OS is: " + os);
        System.out.println("current DIR: " + currentDir);

        if(os.contains("mac")){
            System.setProperty("webdriver.chrome.driver", currentDir + "/src/main/java/com/onehana/onehanadashboard/crawling/driver/forMac/chromedriver_mac_arm64/chromedriver");
        }
        if(os.contains("linux")){
            System.setProperty("webdriver.chrome.driver", currentDir + "/chromedriver_linux64/chromedriver");
        }
        if(os.contains("windows")){
            System.setProperty("webdriver.chrome.driver", currentDir + "/src/main/java/com/onehana/onehanadashboard/crawling/driver/forWindows/chromedriver_win32/chromedriver.exe");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("headless");
        driver = new ChromeDriver(options);
    }

    public void simpleNaverCrawling(List<String> keywords, int quantity) {
        seleniumSetting();

        String url = "https://naver.com";
        driver.get(url);

        driver.findElement(By.xpath("//*[@id=\"NM_FAVORITE\"]/div[1]/ul[2]/li[3]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys("simple");

        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        for (String keyword : keywords) {
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[1]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[1]")).sendKeys(keyword);
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

            int news_cnt = quantity;

            for (int i = 1; i <= 11; i++) {
                if (news_cnt == 0) break;
                if (i == 3) i++;    // 첫 페이지는 2번으로 간 뒤부터 1번 옆에 이전 탭이 생겨서 번호가 1만큼 늘어난다

                try {
                    driver.findElement(By.xpath(String.format("//*[@id=\"contentarea_left\"]/table/tbody/tr/td[%d]/a", i))).click();
                } catch (NoSuchElementException e) {
                    break;
                }

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

                    newsRepository.save(news);

                    news_cnt--;
                    if (news_cnt == 0) {
                        driver.navigate().back();
                        break;
                    }
                    driver.navigate().back();
                }
            }
        }
        driver.close();
        driver.quit();
    }

    public int naver(String keyword, String startDate, String endDate, int quantity) throws NoSuchSessionException {
        seleniumSetting();

        String url = "https://finance.naver.com/news/";
        driver.get(url);

//        driver.findElement(By.xpath("//*[@id=\"NM_FAVORITE\"]/div[1]/ul[2]/li[3]/a")).click();
//        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys(keyword);

        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)),
                Integer.parseInt(startDate.substring(4, 6)),
                Integer.parseInt(startDate.substring(6, 8))); // 시작 날짜

        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)),
                Integer.parseInt(endDate.substring(4, 6)),
                Integer.parseInt(endDate.substring(6, 8))); // 종료 날짜

        int news_cnt = quantity;

        // 시작 날짜부터 종료 날짜까지 하루씩 증가하면서 출력
        for (LocalDate searchDate = start; !searchDate.isAfter(end); searchDate = searchDate.plusDays(1)) {
            if (news_cnt <= 0) break;

            driver.findElement(By.xpath("//*[@id=\"schoption14\"]")).click();

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[1]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/dl/dd[2]/span[5]/label/input[2]")).sendKeys(searchDate.toString());

            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

            boolean hasPage = true; // 아래의 while문 조건
            for (int i = 1; i <= 11; i++) {
                if (news_cnt <= 0) {
                    hasPage = false;
                    break;
                }
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

                    news_cnt--;
                    if (news_cnt == 0) {
                        driver.navigate().back();
                        break;
                    }
                    driver.navigate().back();
                }
                newsRepository.saveAll(allNews);
            }

            // 첫 페이지 이후
            while (hasPage) {
                for (int i = 3; i <= 13; i++) {
                    if (news_cnt <= 0) {
                        hasPage = false;
                        break;
                    }

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

                        news_cnt--;
                        if (news_cnt == 0) {
                            driver.navigate().back();
                            break;
                        }
                        driver.navigate().back();
                    }
                    newsRepository.saveAll(allNews);
                }
            }
        }
        driver.close();
        driver.quit();
        return quantity - news_cnt;
    }
}
