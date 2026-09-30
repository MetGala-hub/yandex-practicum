package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public abstract class BaseUiTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            if (Boolean.getBoolean("headless")) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            if (Boolean.getBoolean("headless")) {
                options.addArguments("--headless=new", "--window-size=1920,1080");
            }
            driver = new ChromeDriver(options);
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
