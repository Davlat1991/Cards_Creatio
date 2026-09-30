package com.eskhata.cards.flows.common;

import com.eskhata.cards.core.config.Config;
import com.eskhata.cards.pages.LoginPage;
import com.eskhata.cards.pages.ShellPage;
import com.eskhata.cards.testdata.models.UserCredentials;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.open;

/**
 * Флоу авторизации — переиспользуемый LEGO-блок.
 * Правило №1: флоу НЕ наследует BasePage; он оркестрирует Page-объекты.
 */
@Slf4j
public class AuthFlow {

    private final LoginPage loginPage = new LoginPage();
    private final ShellPage shellPage = new ShellPage();

    @Step("Авторизация пользователем: {user.login}")
    public void login(UserCredentials user) {
        Config cfg = Config.get();
        String url = cfg.baseUrl() + cfg.loginPath();
        log.info("Открываю экран логина: {}", url);
        open(url);

        loginPage.shouldBeOpened()
                .enterLogin(user.getLogin())
                .enterPassword(user.getPassword())
                .submit();

        shellPage.shouldBeLoggedIn();
        log.info("Авторизация завершена для пользователя '{}'", user.getLogin());
    }
}
