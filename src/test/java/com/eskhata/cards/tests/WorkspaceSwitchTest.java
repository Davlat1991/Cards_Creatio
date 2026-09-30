package com.eskhata.cards.tests;

import com.eskhata.cards.base.BaseTest;
import com.eskhata.cards.flows.common.AuthFlow;
import com.eskhata.cards.flows.common.WorkspaceFlow;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.testdata.models.Workspace;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

/**
 * Блок 1 спайка Freedom UI: смена рабочего места (роли).
 * Собирается из LEGO: AuthFlow + WorkspaceFlow. Проверка — активное место сменилось (ассершен внутри флоу).
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.WORKSPACE)
@Owner("Davlat")
public class WorkspaceSwitchTest extends BaseTest {

    @Test
    @Story(AllureTags.Stories.SWITCH_WORKSPACE)
    @Description("После входа переключение рабочего места на «Верификатор» и проверка, что оно активно")
    @Severity(SeverityLevel.CRITICAL)
    public void canSwitchWorkspace() {
        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        new AuthFlow().login(user);
        new WorkspaceFlow().switchTo(Workspace.VERIFIER);
    }
}
