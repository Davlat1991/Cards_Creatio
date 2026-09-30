package com.eskhata.cards.core.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.eskhata.cards.core.config.Config;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Настройка Selenide-драйвера. Selenide сам держит WebDriver в ThreadLocal,
 * поэтому для параллельного запуска отдельный ThreadLocal-провайдер не нужен —
 * настройки применяем per-thread через Configuration.
 */
@Slf4j
public final class DriverProvider {

    private DriverProvider() {}

    /** Применяет конфигурацию Selenide для текущего потока и подключает Allure-listener. */
    public static void setUp() {
        Config cfg = Config.get();

        Configuration.browser = cfg.browser();
        Configuration.headless = cfg.headless();
        Configuration.timeout = cfg.timeoutMs();
        Configuration.pageLoadStrategy = "normal";
        Configuration.screenshots = true;
        Configuration.savePageSource = false;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        if (cfg.headless()) {
            options.addArguments("--window-size=1920,1080");
        }
        Configuration.browserCapabilities = options;

        if (SelenideLogger.hasListener("AllureSelenide")) {
            SelenideLogger.removeListener("AllureSelenide");
        }
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));

        log.info("Driver настроен: browser={}, headless={}, timeout={}ms",
                cfg.browser(), cfg.headless(), cfg.timeoutMs());
    }

    public static void tearDown() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
            log.info("WebDriver закрыт");
        }
        SelenideLogger.removeListener("AllureSelenide");
    }
}
