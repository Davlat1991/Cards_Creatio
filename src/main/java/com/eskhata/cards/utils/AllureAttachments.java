package com.eskhata.cards.utils;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

/** Вложения в Allure-отчёт (скриншот при падении и т.п.). */
@Slf4j
public final class AllureAttachments {

    private AllureAttachments() {}

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] screenshot() {
        try {
            return ((org.openqa.selenium.TakesScreenshot) com.codeborne.selenide.WebDriverRunner.getWebDriver())
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        } catch (Exception e) {
            log.warn("Не удалось снять скриншот: {}", e.getMessage());
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/html")
    public static String pageSource() {
        try {
            return com.codeborne.selenide.WebDriverRunner.getWebDriver().getPageSource();
        } catch (Exception e) {
            return "";
        }
    }
}
