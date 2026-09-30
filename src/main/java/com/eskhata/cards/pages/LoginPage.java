package com.eskhata.cards.pages;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.core.base.BasePage;
import com.eskhata.cards.core.services.ClickService;
import com.eskhata.cards.core.services.TypeService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница авторизации Creatio (экран NuiLogin).
 *
 * Локаторы подтверждены на живом DOM стенда Creatio 8.3 (правило №10):
 *   - поле логина  : input#loginEdit-el       (обёртка data-item-marker="loginEdit")
 *   - поле пароля  : input#passwordEdit-el     (обёртка data-item-marker="passwordEdit")
 *   - кнопка входа : span[data-item-marker="btnLogin"]  (скрытый двойник btnChangePasswordLogin игнорируется)
 *
 * Локаторы — методы, а не поля (правило №2, защита от StaleElement).
 */
@Slf4j
public class LoginPage extends BasePage {

    // --- Локаторы (МЕТОДЫ, не поля) — подтверждены на живом DOM ---

    private SelenideElement loginInput() {
        return $("#loginEdit-el");
    }

    private SelenideElement passwordInput() {
        return $("#passwordEdit-el");
    }

    private SelenideElement loginButton() {
        return $("[data-item-marker='btnLogin']");
    }

    // --- Действия ---

    @Step("Открыт экран авторизации")
    public LoginPage shouldBeOpened() {
        loginInput().shouldBe(visible, timeout());
        log.info("Экран авторизации отображён");
        return this;
    }

    @Step("Ввести логин")
    public LoginPage enterLogin(String login) {
        TypeService.typeNoTab(loginInput(), login, timeout());
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        TypeService.typeNoTab(passwordInput(), password, timeout());
        return this;
    }

    @Step("Нажать кнопку входа")
    public void submit() {
        ClickService.click(loginButton(), timeout());
        log.info("Кнопка входа нажата");
    }
}
