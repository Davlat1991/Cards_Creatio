package com.eskhata.cards.flows.common;

import com.eskhata.cards.pages.WorkspacePage;
import com.eskhata.cards.testdata.models.Workspace;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

/**
 * Флоу переключения рабочего места (роли).
 * Флоу не наследует BasePage (правило №1); оркестрирует Page-объект.
 */
@Slf4j
public class WorkspaceFlow {

    private final WorkspacePage workspacePage = new WorkspacePage();

    @Step("Переключиться на рабочее место: {workspace}")
    public void switchTo(Workspace workspace) {
        log.info("Переключение на рабочее место: {}", workspace.title());
        workspacePage.openSwitcher()
                .selectWorkspace(workspace)
                .shouldBeActive(workspace);
    }
}
