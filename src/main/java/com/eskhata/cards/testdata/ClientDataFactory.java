package com.eskhata.cards.testdata;

import com.eskhata.cards.testdata.models.ClientData;

/**
 * Фабрика тестовых данных клиента (правило №7 — данные через фабрику, не хардкод в тестах).
 */
public final class ClientDataFactory {

    private ClientDataFactory() {}

    /** Тестовое физлицо: только ФИО (безопасные текстовые поля). Расширяй по мере надобности. */
    public static ClientData physicalPerson() {
        return ClientData.builder()
                .lastName("Тестов")
                .firstName("Тест")
                .middleName("Тестович")
                .build();
    }
}
