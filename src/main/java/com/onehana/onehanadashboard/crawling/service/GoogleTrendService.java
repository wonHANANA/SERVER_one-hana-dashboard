package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.entity.GoogleKeywordTrend;
import com.onehana.onehanadashboard.crawling.entity.GoogleTrend;
import com.onehana.onehanadashboard.crawling.repository.GoogleTrendRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String os = System.getProperty("os.name").toLowerCase();
        String currentDir = new File("").getAbsolutePath();
        System.out.println("OS is: " + os);
        System.out.println("current DIR: " + currentDir);
        if(os.contains("mac")){
            System.setProperty("webdriver.chrome.driver", currentDir + "/src/main/java/com/onehana/onehanadashboard/crawling/driver/forMac/chromedriver_mac_arm64/chromedriver");
        }
        if(os.contains("linux")){
            System.setProperty("webdriver.chrome.driver", currentDir + "/home/ubuntu/chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("download.default_directory=/Users/joonhwi/Desktop/KAU/5-1/wonHana/one-hana-dashboard/src/main/java/com/onehana/onehanadashboard/crawling/downloads/");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        try {
            oneKeywordFastFiveGoogleTrend(keyword);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Dimension screenSize = new Dimension(1920, 1080);
            driver.manage().window().setSize(screenSize);
            wait.until(ExpectedConditions.titleContains("trends.google.co.kr"));
            Thread.sleep(2000);
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
        String currentUrl = driver.getCurrentUrl();
        String newUrl = currentUrl.replace("date=now%201-d", "date=today%205-y");

        driver.get(newUrl);


        //reach to  CSV
        String downloadDirectory = "../downloads/";
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", downloadDirectory);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/md-content/div/div/div[1]/trends-widget/ng-include/widget/div/div/div/widget-actions/div/button[1]/i")));
        WebElement downloadLink = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/md-content/div/div/div[1]/trends-widget/ng-include/widget/div/div/div/widget-actions/div/button[1]/i"));
        downloadLink.click();

        //download CSV
        File downloadedFile = new File(downloadDirectory + keyword+"_5year.csv");
        while(!downloadedFile.exists()) {
            Thread.sleep(1000);
        }
        return "working";

    }

}
