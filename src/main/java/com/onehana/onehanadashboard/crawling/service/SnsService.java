package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.config.BaseResponseStatus;
import com.onehana.onehanadashboard.crawling.dto.response.SNSResponse;
import com.onehana.onehanadashboard.crawling.entity.SNS;
import com.onehana.onehanadashboard.crawling.repository.SnsRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SnsService {

    private final SnsRepository snsRepository;

    public SNSResponse getComments(String youtuber) {
        List<SNS> snsList = snsRepository.findAllByKeyman(youtuber);
        if (snsList.size() == 0)
            throw new BaseException(BaseResponseStatus.DATABASE_NOT_FOUND);

        List<String> textList = snsList.stream()
                .map(SNS::getText).toList();

        SNSResponse sns = SNSResponse.builder()
                .keyman(snsList.get(0).getKeyman())
                .category(snsList.get(0).getCategory())
                .text(textList)
                .build();

        return sns;
    }

    public void YoutubeCrawling(String youtuber, String category) throws InterruptedException {
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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("window-size=1920x1080");
        options.addArguments("lang=ko_KR");
        options.setExperimentalOption("detach", true);
        options.addArguments("disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.youtube.com/");
        driver.manage().window().maximize();

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("[aria-label = 검색]")).sendKeys(youtuber);
        Thread.sleep(2000);
//        driver.findElement(By.cssSelector("[aria-label = 검색]")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("[aria-label = 검색]")).click();
        driver.findElement(By.cssSelector("[aria-label = 검색]")).sendKeys(Keys.ENTER);
        System.out.println("Enter sent");

        Thread.sleep(10000);
        driver.findElement(By.cssSelector("#main-link")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#tabsContent > tp-yt-paper-tab:nth-child(5) > div > div.tab-title.style-scope.ytd-c4-tabbed-header-renderer")).click();

        String youtubeSource = driver.getPageSource();
        Document html = Jsoup.parse(youtubeSource);
        Elements info = html.select("ytd-browse[page-subtype=channels]");

        Elements videoInfo = info.select("a#video-title.yt-simple-endpoint.style-scope.ytd-grid-video-renderer");
        List<List<String>> urlList = new ArrayList<>();

        for (Element element : videoInfo) {
            String title = element.select("[title]").text().replace("\n", "");
            String url = "https://www.youtube.com" + element.attr("href");
            List<String> videoData = new ArrayList<>();

            videoData.add(title);
            videoData.add(url);
            urlList.add(videoData);
        }
        driver.close();

        int fileIndex = 1;
        for (List<String> videoData : urlList) {
            String title = videoData.get(0);
            String url = videoData.get(1);

            Map<String, String> initialData = new HashMap<>();
            initialData.put("title", title);

            List<Map<String, String>> dataList = new ArrayList<>();
            dataList.add(initialData);

            driver = new ChromeDriver(options);
            driver.get(url);
            Thread.sleep(1000);

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 600)");
            Thread.sleep(3000);

            long lastHeight = ((Number) ((JavascriptExecutor) driver).executeScript("return document.documentElement.scrollHeight")).longValue();

            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
                Thread.sleep(1000);
                long newHeight = ((Number) ((JavascriptExecutor) driver).executeScript("return document.documentElement.scrollHeight")).longValue();
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
            Thread.sleep(500);

            String htmlSource = driver.getPageSource();
            Document commentDoc = Jsoup.parse(htmlSource);
            Elements commentsList = commentDoc.select("ytd-comment-thread-renderer.style-scope.ytd-item-section-renderer");

            List<SNS> snsList = new ArrayList<>();
            for (Element text : commentsList) {
                String comment = Objects.requireNonNull(text.selectFirst("yt-formatted-string[id=content-text]"))
                        .text().replace('\n', ' ');

                if (comment.length() > 80 || comment.length() < 5)
                    continue;

                Map<String, String> commentData = new HashMap<>();
                commentData.put("comment", comment);
                dataList.add(commentData);

                SNS sns = SNS.builder()
                        .keyman(youtuber)
                        .category(category)
                        .text(comment)
                        .build();

                snsList.add(sns);
            }
            snsRepository.saveAll(snsList);

            fileIndex++;
            driver.close();
        }
        driver.quit();
    }
}