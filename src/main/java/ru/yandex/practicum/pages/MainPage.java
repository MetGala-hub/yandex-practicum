package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    public static final String DEFAULT_URL = "https://qa-scooter.education-services.ru/";

    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");

    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");

    private final By topOrderButton = By.className("Button_Button__ra12g");

    private final By bottomOrderButton = By.xpath("//div[contains(@class,'Home_FinishButton')]//button[text()='Заказать']");

    private final By faqHeading = By.xpath("//*[text()='Вопросы о важном']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public MainPage open() {
        driver.get(System.getProperty("baseUrl", DEFAULT_URL));
        wait.until(ExpectedConditions.visibilityOfElementLocated(scooterLogo));
        return this;
    }

    public void clickOrderButton(OrderButton position) {
        By locator = position == OrderButton.TOP ? topOrderButton : bottomOrderButton;
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        button.click();
    }

    public String openFaqAndGetAnswer(int index) {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(faqHeading));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", heading);
        By question = By.id("accordion__heading-" + index);
        By answer = By.id("accordion__panel-" + index);
        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }

    public void clickScooterLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(scooterLogo)).click();
    }

    public void clickYandexLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(yandexLogo)).click();
    }

    public enum OrderButton {TOP, BOTTOM}
}
