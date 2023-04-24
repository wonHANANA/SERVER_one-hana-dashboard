package com.onehana.onehanadashboard.crawling.service;

import com.onehana.onehanadashboard.crawling.dto.KeywordDto;
import com.onehana.onehanadashboard.crawling.entity.Keyword;
import com.onehana.onehanadashboard.crawling.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private WebDriver driver;

    public KeywordDto addKeyword(KeywordDto keywordDto) {
        return keywordRepository.save(keywordDto.toEntity()).toDto();
    }

    @Transactional(readOnly = true)
    public List<KeywordDto> findEsgKeyword() {
        List<Keyword> keywords = keywordRepository.findAll();

        return keywords.stream()
                .map(KeywordDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void countNewsWithKeyword() {
        System.setProperty("webdriver.chrome.driver", "/Users/idonghyun/IdeaProjects/hana/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("headless");
        driver = new ChromeDriver(options);

        String url = "https://naver.com";
        driver.get(url);

        driver.findElement(By.xpath("//*[@id=\"NM_FAVORITE\"]/div[1]/ul[2]/li[3]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[6]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/input")).sendKeys("1");
        driver.findElement(By.xpath("//*[@id=\"newsMainTop\"]/div/div[2]/form/div/a/input")).click();

        List<Keyword> keywords = keywordRepository.findAll();
        List<String> names = keywords.stream()
                .map(Keyword::getName).toList();

        for (String name : names) {
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[1]")).clear();
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[1]")).sendKeys(name);
            driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/form/div/div/div/input[2]")).click();

            int newsCnt = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"contentarea_left\"]/div[2]/p/strong[2]"))
                    .getText().replace(",", ""));

            Keyword keyword = keywordRepository.findByName(name);
            keyword.setNewsCnt(newsCnt);
            keywordRepository.save(keyword);
        }
        driver.close();
        driver.quit();
    }
}
