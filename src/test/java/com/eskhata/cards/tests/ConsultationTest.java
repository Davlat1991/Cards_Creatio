package com.eskhata.cards.tests;

import com.eskhata.cards.base.BaseTest;
import com.eskhata.cards.flows.common.AuthFlow;
import com.eskhata.cards.flows.common.ConsultationFlow;
import com.eskhata.cards.flows.common.WorkspaceFlow;
import com.eskhata.cards.testdata.ClientDataFactory;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.ClientData;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.testdata.models.Workspace;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

/**
 * Блок 2 спайка Freedom UI: заполнение данных клиента в панели консультации.
 * Реальный порядок: вход → выбор рабочего места → открыть панель (если закрыта) → заполнить ФИО.
 * Поля ищутся по placeholder (id/marker = GUID). Проверка — поля содержат введённое (ассершен во флоу).
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.CONSULTATION)
@Owner("Davlat")
public class ConsultationTest extends BaseTest {

    @Test
    @Story(AllureTags.Stories.CLIENT_SEARCH)
    @Description("Вход, выбор рабочего места, открытие панели и заполнение ФИО клиента с проверкой")
    @Severity(SeverityLevel.CRITICAL)
    public void canFillClientData() {
        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        new AuthFlow().login(user);
        new WorkspaceFlow().switchTo(Workspace.VERIFIER);

        ClientData client = ClientDataFactory.physicalPerson();
        new ConsultationFlow().fillClient(client);   // ensurePanelOpen внутри
    }
}
