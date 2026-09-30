package com.eskhata.cards.core.services;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

/**
 * Клики с ожиданием готовности элемента.
 * Правило проекта №3: без Thread.sleep — только shouldBe с Duration.
 */
@Slf4j
public final class ClickService {

    private ClickService() {}

    @Step("Кликнуть по элементу")
    public static void click(SelenideElement element, Duration timeout) {
        element.shouldBe(visible, timeout).shouldBe(enabled).click();
        log.info("Клик выполнен");
    }
}
