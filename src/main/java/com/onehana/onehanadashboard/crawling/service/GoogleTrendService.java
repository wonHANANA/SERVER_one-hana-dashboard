package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.GoogleKeywordTrend;
import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.repository.GoogleTrendRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleTrendService {

    private final GoogleTrendRepository googleTrendRepository;

    public List<GoogleTrend> saveGoogleTrend(List<GoogleTrend> googleTrend) {
        return googleTrendRepository.saveAll(googleTrend);
    }
    private WebDriver driver;
    private WebElement web;

    public void process(String keyword) {
        System.setProperty("webdriver.chrome.driver", "/Users/joonhwi/Desktop/KAU/5-1/wonHana/one-hana-dashboard/src/main/java/com/onehana/onehanadashboard/crawling/driver/forMac/chromedriver_mac_arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        try {
            oneKeywordFastFiveGoogleTrend(keyword);

            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
    }

    public String oneKeywordFastFiveGoogleTrend(String keyword) throws InterruptedException{
        String url = "https://trends.google.co.kr/home?geo=KR&hl=ko";
        String date;
        int dailySearch;

        driver.get(url);

        //키워드로 검색
        driver.findElement(By.xpath("//*[@id=\"i11\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"i11\"]")).sendKeys(keyword);
        driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[4]/div/c-wiz[1]/div/div[1]/div[3]/div/div[2]/div[3]/div/button/span")).click();

        //5년간 데이터 검색
        driver.findElement(By.xpath("//*[@id=\"select_value_label_9\"]/span[2]")).click();
        driver.findElement(By.linkText("지난 5년")).click();


        return "working";

    }

}
