package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final By firstName = By.xpath("//input[@placeholder='* Имя']");

    private final By lastName = By.xpath("//input[@placeholder='* Фамилия']");

    private final By address = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    private final By metro = By.xpath("//input[@placeholder='* Станция метро']");

    private final By phone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    private final By nextButton = By.xpath("//button[text()='Далее']");

    private final By deliveryDate = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    private final By rentalPeriod = By.className("Dropdown-control");

    private final By orderButton = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");

    private final By confirmButton = By.xpath("//button[text()='Да']");

    private final By successHeading = By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Заказ оформлен')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public OrderPage fillCustomer(String name, String surname, String customerAddress,
                                  String metroStation, String customerPhone) {
        type(firstName, name);
        type(lastName, surname);
        type(address, customerAddress);
        wait.until(ExpectedConditions.elementToBeClickable(metro)).click();
        driver.findElement(metro).sendKeys(metroStation);
        By stationOption = By.xpath("//div[contains(@class,'select-search__select')]//*[contains(normalize-space(.),'" + metroStation + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(stationOption)).click();
        type(phone, customerPhone);
        return this;
    }

    public OrderPage clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDate));
        return this;
    }

    public OrderPage fillRental(String date, String period, String colour, String comment) {
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(deliveryDate));
        dateInput.sendKeys(date, Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriod)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + period + "']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='" + colour + "']"))).click();
        type(By.xpath("//input[@placeholder='Комментарий для курьера']"), comment);
        return this;
    }

    public void submitAndConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading)).isDisplayed();
    }

    public void submitEmptyCustomerForm() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public int visibleValidationErrors() {
        return driver.findElements(By.xpath("//*[contains(@class,'Input_ErrorMessage') and string-length(normalize-space()) > 0]")).size();
    }

    private void type(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    }
}
