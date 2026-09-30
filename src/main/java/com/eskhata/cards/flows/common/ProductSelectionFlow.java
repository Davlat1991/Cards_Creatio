package com.eskhata.cards.flows.common;

import com.eskhata.cards.pages.ProductSelectionPage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/**
 * Флоу выбора продукта на странице заявки. Флоу не наследует BasePage (правило №1).
 */
@Slf4j
public class ProductSelectionFlow {

    private final ProductSelectionPage productPage = new ProductSelectionPage();

    @Step("Выбрать вид продукта и проверить: {productKind}")
    public void selectProductKind(String productKind) {
        productPage.waitLoaded()
                .selectProductKind(productKind)
                .shouldHaveProductKind(productKind);
    }
}
