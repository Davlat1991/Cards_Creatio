package com.eskhata.cards.testdata;

import com.eskhata.cards.testdata.models.CardProductData;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/** Фабрика параметров карточного продукта (CREATIO-439). Данные — из card-products.json. */
@Slf4j
public final class CardProductFactory {

    public static final String VISA_GOLD_DEBIT = "visa-gold-debit";

    private CardProductFactory() {}

    /** Дебетовая Visa GOLD, 60 мес. — продукт из тест-кейса CREATIO-439. */
    public static CardProductData visaGoldDebit() {
        return byAlias(VISA_GOLD_DEBIT);
    }

    @Step("Тест-данные продукта: {alias}")
    public static CardProductData byAlias(String alias) {
        CardProductData product = TestDataLoader.get().product(alias);
        log.info("Продукт [{}]: {}", alias, product);
        return product;
    }
}