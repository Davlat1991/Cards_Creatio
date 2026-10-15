package com.eskhata.cards.tests;

import com.eskhata.cards.base.BaseTest;
import com.eskhata.cards.flows.common.AuthFlow;
import com.eskhata.cards.flows.common.ConsultationFlow;
import com.eskhata.cards.flows.common.WorkspaceFlow;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.testdata.models.Workspace;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

/**
 * Тест №3 S1: панель консультации — открыть / закрыть / ensure.
 * LEGO: AuthFlow + WorkspaceFlow + ConsultationFlow.
 * Каждое действие с панелью сопровождается проверкой флага body[right-panel-collapsed].
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.CONSULTATION)
@Owner("Davlat")
public class ConsultationPanelTest extends BaseTest {

    @Test
    @Story(AllureTags.Stories.CONSULTATION_PANEL)
    @Description("После входа и выбора «Верификатора»: закрыть панель, открыть, повторно открыть (ensure без побочных эффектов), закрыть — с проверкой состояния на каждом шаге")
    @Severity(SeverityLevel.CRITICAL)
    public void canOpenAndClosePanel() {
        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        new AuthFlow().login(user);
        new WorkspaceFlow().switchTo(Workspace.VERIFIER);

        ConsultationFlow panel = new ConsultationFlow();
        panel.closePanelAndVerify();   // исходное состояние приводим к «закрыта»
        panel.openPanelAndVerify();    // открытие
        panel.openPanelAndVerify();    // ensure: повторный вызов не должен закрыть панель
        panel.closePanelAndVerify();   // закрытие
    }
}