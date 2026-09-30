package com.eskhata.cards.core.services;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

/**
 * Ввод текста. Правило проекта №9: НЕ используем setValue() —
 * в Creatio он не триггерит onChange. Печатаем посимвольно + Keys.TAB.
 */
@Slf4j
public final class TypeService {

    private TypeService() {}

    @Step("Ввести значение '{value}' в поле")
    public static void type(SelenideElement field, String value, Duration timeout) {
        field.shouldBe(visible, timeout).shouldBe(enabled).click();
        field.clear();
        for (char c : value.toCharArray()) {
            field.sendKeys(String.valueOf(c));
        }
        field.sendKeys(Keys.TAB);
        log.info("Введено значение в поле (len={})", value.length());
    }

    @Step("Ввести значение в поле без TAB")
    public static void typeNoTab(SelenideElement field, String value, Duration timeout) {
        field.shouldBe(visible, timeout).shouldBe(enabled).click();
        field.clear();
        for (char c : value.toCharArray()) {
            field.sendKeys(String.valueOf(c));
        }
        log.info("Введено значение (без TAB, len={})", value.length());
    }
}
