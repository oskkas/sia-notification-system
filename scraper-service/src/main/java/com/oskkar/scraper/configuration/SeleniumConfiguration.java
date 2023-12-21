package com.oskkar.scraper.configuration;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.time.Duration;

@Configuration
public class SeleniumConfiguration {
    @PostConstruct
    void postConstruct() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
    }

    @Bean
    public ChromeDriver driver() {
        return new ChromeDriver();
    }
    @Bean
    @DependsOn("driver")
    public WebDriverWait wait(ChromeDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
