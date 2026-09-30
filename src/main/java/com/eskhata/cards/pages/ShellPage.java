package com.eskhata.cards.pages;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.core.base.BasePage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Экран после входа — шелл Freedom UI.
 * Позитивная проверка входа: появился корневой контейнер, в который Freedom UI
 * рендерит страницы (кастомный тег crt-schema-outlet). Локатор подтверждён на живом DOM 8.3.
 * Не завязываемся на id — в Freedom UI id пустые/нестабильные (правило №10).
 */
@Slf4j
public class ShellPage extends BasePage {

    private SelenideElement appShell() {
        return $("crt-schema-outlet");
    }

    @Step("Проверить, что вход выполнен (загружен шелл Freedom UI)")
    public ShellPage shouldBeLoggedIn() {
        // Шелл грузится не мгновенно — даём запас по времени.
        appShell().shouldBe(visible, Duration.ofSeconds(60));
        log.info("Шелл Freedom UI загружен — авторизация успешна");
        return this;
    }
}
