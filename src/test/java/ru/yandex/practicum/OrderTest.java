package ru.yandex.practicum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest extends BaseUiTest {
    private static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of(MainPage.OrderButton.TOP, "Иван", "Иванов", "Москва, Лесная, 5", "Сокольники", "+79991234567", "30.12.2026", "сутки", "чёрный жемчуг", "Позвонить заранее"),
                Arguments.of(MainPage.OrderButton.BOTTOM, "Анна", "Петрова", "Москва, Арбат, 10", "Арбатская", "89997654321", "31.12.2026", "двое суток", "серая безысходность", "Оставить у подъезда")
        );
    }

    @ParameterizedTest(name = "Заказ через кнопку {0}")
    @MethodSource("orderData")
    void customerCanCreateOrder(MainPage.OrderButton button, String name, String surname,
                                String address, String metro, String phone, String date,
                                String period, String colour, String comment) {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickOrderButton(button);

        OrderPage orderPage = new OrderPage(driver)
                .fillCustomer(name, surname, address, metro, phone)
                .clickNext()
                .fillRental(date, period, colour, comment);
        orderPage.submitAndConfirm();

        assertTrue(orderPage.isSuccessMessageDisplayed(), "Должно появиться сообщение об успешном заказе");
    }
}
