package com.eskhata.cards.pages;

import com.codeborne.selenide.SelenideElement;
import com.eskhata.cards.core.base.BasePage;
import com.eskhata.cards.core.services.ClickService;
import com.eskhata.cards.testdata.models.Workspace;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

/**
 * Панель навигации Freedom UI: переключение рабочего места (роли).
 * Локаторы подтверждены на живом DOM 8.3:
 *   - переключатель (показывает активное место): crt-button[data-item-marker='NavigationPanelGroupItems']
 *   - пункт списка рабочих мест: button[data-item-marker='<Название>']
 * Локаторы — методы (правило №2). На id не завязываемся (правило №10).
 */
@Slf4j
public class WorkspacePage extends BasePage {

    /** Переключатель рабочих мест; его текст = активное рабочее место. */
    private SelenideElement switcher() {
        return $("[data-item-marker='NavigationPanelGroupItems']");
    }

    /** Пункт конкретного рабочего места в раскрытом списке (тег button, не crt-button). */
    private SelenideElement workspaceItem(Workspace workspace) {
        return $("button[data-item-marker='" + workspace.title() + "']");
    }

    @Step("Открыть переключатель рабочих мест")
    public WorkspacePage openSwitcher() {
        ClickService.click(switcher(), timeout());
        return this;
    }

    @Step("Выбрать рабочее место: {workspace}")
    public WorkspacePage selectWorkspace(Workspace workspace) {
        ClickService.click(workspaceItem(workspace), timeout());
        log.info("Выбрано рабочее место: {}", workspace.title());
        return this;
    }

    @Step("Проверить, что активно рабочее место: {workspace}")
    public WorkspacePage shouldBeActive(Workspace workspace) {
        switcher().shouldHave(text(workspace.title()), timeout());
        log.info("Активное рабочее место подтверждено: {}", workspace.title());
        return this;
    }
}
