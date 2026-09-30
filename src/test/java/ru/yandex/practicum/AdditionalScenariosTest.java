package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.OrderPage;
import ru.yandex.practicum.pages.OrderStatusPage;

import java.time.Duration;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdditionalScenariosTest extends BaseUiTest {
    @Test
    void scooterLogoReturnsToHomePage() {
        MainPage page = new MainPage(driver).open();
        page.clickOrderButton(MainPage.OrderButton.TOP);
        page.clickScooterLogo();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getCurrentUrl().matches(".*/?$"));
        assertEquals(System.getProperty("baseUrl", MainPage.DEFAULT_URL), driver.getCurrentUrl());
    }

    @Test
    void emptyRequiredFieldsShowErrors() {
        MainPage page = new MainPage(driver).open();
        page.clickOrderButton(MainPage.OrderButton.TOP);
        OrderPage orderPage = new OrderPage(driver);
        orderPage.submitEmptyCustomerForm();
        assertEquals(5, orderPage.visibleValidationErrors());
    }

    @Test
    void yandexLogoOpensYandexInNewWindow() {
        MainPage page = new MainPage(driver).open();
        String originalWindow = driver.getWindowHandle();
        page.clickYandexLogo();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> d.getWindowHandles().size() > 1);
        String newWindow = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(originalWindow))
                .findFirst()
                .orElseThrow();
        driver.switchTo().window(newWindow);
        wait.until(d -> !"about:blank".equals(d.getCurrentUrl()));

        String openedUrl = driver.getCurrentUrl();
        String host = URI.create(openedUrl).getHost();
        boolean isYandexHomePage = host != null && (
                host.equals("ya.ru") || host.endsWith(".ya.ru")
                        || host.equals("yandex.ru") || host.endsWith(".yandex.ru")
                        || host.equals("dzen.ru") || host.endsWith(".dzen.ru"));
        assertTrue(isYandexHomePage,
                "Логотип Яндекса должен открыть главную страницу Яндекса, но открыт: " + openedUrl);
    }

    @Test
    void invalidOrderNumberShowsNotFound() {
        new MainPage(driver).open();
        OrderStatusPage statusPage = new OrderStatusPage(driver).search("000000000000");
        assertTrue(statusPage.isNotFoundDisplayed());
    }
}
