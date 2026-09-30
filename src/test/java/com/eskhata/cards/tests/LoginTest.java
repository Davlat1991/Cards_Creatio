package com.eskhata.cards.tests;

import com.eskhata.cards.base.BaseTest;
import com.eskhata.cards.flows.common.AuthFlow;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

/**
 * Спайк-тест: обвязка работает end-to-end —
 * драйвер стартует, открывается стенд, проходит авторизация, грузится шелл Freedom UI.
 * Зелёная авторизация подтверждает обвязку (драйвер, конфиг, сборка, Allure, доступ к среде).
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.AUTH)
@Owner("Davlat")
public class LoginTest extends BaseTest {

    @Test
    @Story(AllureTags.Stories.LOGIN)
    @Description("Успешный вход оператора карт и загрузка шелла Freedom UI")
    @Severity(SeverityLevel.BLOCKER)
    public void cardOperatorCanLogIn() {
        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        new AuthFlow().login(user);
    }
}
