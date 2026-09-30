package com.eskhata.cards.components;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.core.services.ClickService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Переиспользуемый компонент выпадающего списка (combobox) Freedom UI.
 * Подтверждено на живом DOM 8.3: клик по полю раскрывает список
 * div.listview > ul > li, пункт выбираем по тексту (пункты помечены GUID — берём текстом).
 */
@Slf4j
public class LookupComponent {

    /** Пункт раскрытого списка по видимому тексту. */
    private SelenideElement option(String text) {
        return $x("//div[contains(@class,'listview')]//li[normalize-space(.)='" + text + "']");
    }

    @Step("Выбрать в выпадающем списке: {optionText}")
    public void select(SelenideElement field, String optionText, Duration timeout) {
        ClickService.click(field, timeout);            // раскрыть список
        option(optionText).shouldBe(visible, timeout).click();
        log.info("В combobox выбрано: {}", optionText);
    }
}
