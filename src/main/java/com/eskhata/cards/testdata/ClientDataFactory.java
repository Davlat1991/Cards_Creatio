package com.eskhata.cards.testdata;

import com.eskhata.cards.testdata.models.ClientData;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/**
 * Фабрика тестовых данных клиента (правило №7). Данные — из JSON через TestDataLoader,
 * в коде значений нет. Команда «Карта» меняет клиентов в clients.json, не трогая тесты.
 */
@Slf4j
public final class ClientDataFactory {

    /** Верифицированный розничный клиент — основной для сценариев консультации и заявки. */
    public static final String RETAIL_VERIFIED = "retail-verified";

    private ClientDataFactory() {}

    /** Тестовое физлицо по умолчанию (верифицированный, розница). */
    public static ClientData physicalPerson() {
        return byAlias(RETAIL_VERIFIED);
    }

    @Step("Тест-данные клиента: {alias}")
    public static ClientData byAlias(String alias) {
        ClientData client = TestDataLoader.get().client(alias);
        log.info("Клиент [{}]: {}", alias, client);
        return client;
    }
}