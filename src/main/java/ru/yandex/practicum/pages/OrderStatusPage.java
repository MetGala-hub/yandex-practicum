package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderStatusPage {

    private final By statusButton = By.xpath("//button[text()='Статус заказа']");

    private final By orderNumber = By.xpath("//input[@placeholder='Введите номер заказа']");

    private final By goButton = By.xpath("//button[text()='Go!']");

    private final By notFound = By.xpath("//img[contains(@alt,'Not found') or contains(@src,'not-found')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public OrderStatusPage search(String number) {
        wait.until(ExpectedConditions.elementToBeClickable(statusButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumber)).sendKeys(number);
        wait.until(ExpectedConditions.elementToBeClickable(goButton)).click();
        return this;
    }

    public boolean isNotFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(notFound)).isDisplayed();
    }
}
