package com.eskhata.cards.pages;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.components.LookupComponent;
import com.eskhata.cards.core.base.BasePage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница заявки — выбор продукта.
 * Локаторы подтверждены на живом DOM 8.3 — здесь маркеры СТАБИЛЬНЫЕ (не GUID):
 *   - «Вид продукта» (combobox): input[data-item-marker='SelectionProductKind']
 *   - (на будущее) SpecificationListItem, IntegerValue, FloatValue — тоже стабильны.
 * Combobox обслуживается LookupComponent.
 */
@Slf4j
public class ProductSelectionPage extends BasePage {

    /** Заявка по hash-route открывается через перезагрузку шелла — SPA рисуется дольше обычного таймаута. */
    private static final Duration PAGE_LOAD = Duration.ofSeconds(60);

    private final LookupComponent lookup = new LookupComponent();

    private SelenideElement appShell() {
        return $("crt-schema-outlet");
    }

    private SelenideElement productKindField() {
        // Маркер SelectionProductKind висит на div-обёртке; сам input внутри неё.
        return $("[data-item-marker='SelectionProductKind'] input");
    }

    @Step("Дождаться загрузки страницы заявки (поле «Вид продукта» видно)")
    public ProductSelectionPage waitLoaded() {
        appShell().shouldBe(visible, PAGE_LOAD);
        productKindField().shouldBe(visible, PAGE_LOAD);
        log.info("Страница заявки загружена, поле «Вид продукта» доступно");
        return this;
    }

    @Step("Выбрать вид продукта: {productKind}")
    public ProductSelectionPage selectProductKind(String productKind) {
        lookup.select(productKindField(), productKind, timeout());
        return this;
    }

    @Step("Проверить выбранный вид продукта: {productKind}")
    public ProductSelectionPage shouldHaveProductKind(String productKind) {
        productKindField().shouldHave(value(productKind), timeout());
        log.info("Вид продукта выбран и подтверждён: {}", productKind);
        return this;
    }
}
