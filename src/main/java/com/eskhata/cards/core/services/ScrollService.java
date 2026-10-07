package com.eskhata.cards.core.services;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

/**
 * Прокрутка к элементу. Правило проекта №3: без Thread.sleep.
 *
 * Прокрутка к ЦЕНТРУ экрана (block:'center'), а не к верхней кромке —
 * иначе элемент уходит под закреплённую шапку страницы.
 * Подтверждено на 7.18; в Freedom UI 8.3 тот же класс проблемы.
 */
@Slf4j
public final class ScrollService {

    private ScrollService() {}

    /** Опции JS-метода scrollIntoView: мгновенно, по центру по обеим осям. */
    private static final String CENTER = "{behavior: 'instant', block: 'center', inline: 'center'}";

    @Step("Прокрутить к элементу (центр экрана)")
    public static SelenideElement toCenter(SelenideElement element, Duration timeout) {
        element.shouldBe(visible, timeout);
        element.scrollIntoView(CENTER);
        log.info("Прокрутка к элементу выполнена (центр экрана)");
        return element;
    }
}