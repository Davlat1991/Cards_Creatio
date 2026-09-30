package com.eskhata.cards.flows.common;

import com.eskhata.cards.pages.ConsultationPage;
import com.eskhata.cards.testdata.models.ClientData;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/**
 * Флоу работы с панелью консультации. Флоу не наследует BasePage (правило №1).
 * Панель может быть закрыта после входа — перед работой всегда открываем (ensurePanelOpen).
 */
@Slf4j
public class ConsultationFlow {

    private final ConsultationPage consultationPage = new ConsultationPage();

    /** Универсальные операции с панелью — использовать по необходимости. */
    @Step("Открыть панель консультации")
    public void openPanel() {
        consultationPage.openPanel();
    }

    @Step("Закрыть панель консультации")
    public void closePanel() {
        consultationPage.closePanel();
    }

    @Step("Заполнить данные клиента и проверить заполнение")
    public void fillClient(ClientData client) {
        log.info("Заполнение данных клиента: {} {}", client.getLastName(), client.getFirstName());
        consultationPage.ensurePanelOpen()
                .fillClient(client)
                .shouldBeFilled(client);
    }

    @Step("Заполнить данные клиента и выполнить поиск")
    public void fillClientAndSearch(ClientData client) {
        consultationPage.ensurePanelOpen()
                .fillClient(client)
                .shouldBeFilled(client)
                .clickSearch();
    }
}
