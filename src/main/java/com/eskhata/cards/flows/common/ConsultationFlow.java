package com.eskhata.cards.flows.common;

import com.eskhata.cards.pages.ConsultationPage;
import com.eskhata.cards.testdata.models.ClientData;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/**
 * Флоу работы с панелью консультации. Флоу не наследует BasePage (правило №1).
 */
@Slf4j
public class ConsultationFlow {

    private final ConsultationPage consultationPage = new ConsultationPage();

    @Step("Заполнить данные клиента и проверить заполнение")
    public void fillClient(ClientData client) {
        log.info("Заполнение данных клиента: {} {}", client.getLastName(), client.getFirstName());
        consultationPage.fillClient(client).shouldBeFilled(client);
    }

    @Step("Заполнить данные клиента и выполнить поиск")
    public void fillClientAndSearch(ClientData client) {
        consultationPage.fillClient(client).shouldBeFilled(client).clickSearch();
    }
}
