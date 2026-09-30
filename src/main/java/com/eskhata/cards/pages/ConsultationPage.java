package com.eskhata.cards.pages;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.core.base.BasePage;
import com.eskhata.cards.core.services.ClickService;
import com.eskhata.cards.core.services.TypeService;
import com.eskhata.cards.testdata.models.ClientData;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Панель «Консультационный центр» → «Начать консультацию» (ФИЗ.ЛИЦО).
 *
 * Стратегия локаторов Freedom UI (подтверждено на живом DOM 8.3):
 *   - ПОЛЯ ВВОДА: id и data-item-marker — пересоздаваемые GUID (нестабильны) → НЕ использовать.
 *     Ищем по СТАБИЛЬНОМУ placeholder (как в 7.18).
 *   - КНОПКИ: стабильные маркеры SearchButton / ClearButton / AnonymousConsultationButton.
 * Локаторы — методы (правило №2).
 */
@Slf4j
public class ConsultationPage extends BasePage {

    /**
     * Кнопка-переключатель панели консультации в правом тулбаре.
     * TODO VERIFY на живом DOM: маркер взят из дампа ('CommunicationPanel').
     * Если open() кликает не ту иконку — навести мышь на нужную кнопку в правом тулбаре,
     * снять её data-item-marker и заменить строку ниже (одно место).
     */
    private static final String PANEL_TOGGLE_MARKER = "Коммуникационная панель";

    private SelenideElement panelToggle() {
        return $("[data-item-marker='" + PANEL_TOGGLE_MARKER + "']");
    }

    /** Поле по placeholder — стабильно; id/marker у полей = GUID, не годятся. */
    private SelenideElement field(String placeholder) {
        return $x("//input[@placeholder='" + placeholder + "']");
    }

    private SelenideElement searchButton() {
        return $("[data-item-marker='SearchButton']");
    }

    // ---------- Панель: открыть / закрыть / переключить (универсально) ----------

    /** Открыта ли панель — по флагу body[right-panel-collapsed='false'] (быстро, без ожидания). */
    public boolean isPanelOpen() {
        return "false".equals($("body").getAttribute("right-panel-collapsed"));
    }

    @Step("Открыть панель консультации, если закрыта")
    public ConsultationPage ensurePanelOpen() {
        if (!isPanelOpen()) {
            log.info("Панель консультации закрыта — открываю");
            ClickService.click(panelToggle(), timeout());
            $("body").shouldHave(attribute("right-panel-collapsed", "false"), timeout());
        } else {
            log.info("Панель консультации уже открыта");
        }
        return this;
    }

    @Step("Открыть панель консультации")
    public ConsultationPage openPanel() {
        return ensurePanelOpen();
    }


    @Step("Закрыть панель консультации, если открыта")
    public ConsultationPage closePanel() {
        if (isPanelOpen()) {
            log.info("Панель консультации открыта — закрываю");
            ClickService.click(panelToggle(), timeout());
            $("body").shouldHave(attribute("right-panel-collapsed", "true"), timeout());
        } else {
            log.info("Панель консультации уже закрыта");
        }
        return this;
    }

    @Step("Переключить панель консультации")
    public ConsultationPage togglePanel() {
        ClickService.click(panelToggle(), timeout());
        return this;
    }

    @Step("Заполнить данные клиента")
    public ConsultationPage fillClient(ClientData c) {
        typeIfPresent("Фамилия", c.getLastName());
        typeIfPresent("Имя", c.getFirstName());
        typeIfPresent("Отчество", c.getMiddleName());
        typeIfPresent("Серия", c.getPassportSeries());
        typeIfPresent("Номер паспорта", c.getPassportNumber());
        typeIfPresent("ИНН", c.getInn());
        typeIfPresent("Договор", c.getContract());
        typeIfPresent("Дата рождения", c.getBirthDate());
        return this;
    }

    @Step("Проверить, что поля клиента заполнены")
    public ConsultationPage shouldBeFilled(ClientData c) {
        assertIfPresent("Фамилия", c.getLastName());
        assertIfPresent("Имя", c.getFirstName());
        assertIfPresent("Отчество", c.getMiddleName());
        assertIfPresent("Серия", c.getPassportSeries());
        assertIfPresent("Номер паспорта", c.getPassportNumber());
        assertIfPresent("ИНН", c.getInn());
        assertIfPresent("Договор", c.getContract());
        // Дата рождения не проверяем значением — date-edit может переформатировать ввод.
        log.info("Поля клиента заполнены корректно");
        return this;
    }

    @Step("Нажать «Поиск»")
    public ConsultationPage clickSearch() {
        ClickService.click(searchButton(), timeout());
        log.info("Кнопка «Поиск» нажата");
        return this;
    }

    private void typeIfPresent(String placeholder, String v) {
        if (v != null && !v.isBlank()) {
            TypeService.type(field(placeholder), v, timeout());
        }
    }

    private void assertIfPresent(String placeholder, String expected) {
        if (expected != null && !expected.isBlank()) {
            field(placeholder).shouldHave(value(expected), timeout());
        }
    }
}
