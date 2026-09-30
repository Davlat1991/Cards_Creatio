package com.eskhata.cards.core.base;

import com.eskhata.cards.core.config.Config;

import java.time.Duration;

/**
 * База для всех Page-объектов. Держит общий таймаут и хелперы,
 * которые нужны страницам. Флоу НЕ наследуют этот класс (правило №1).
 */
public abstract class BasePage {

    protected final Duration timeout = Duration.ofMillis(Config.get().timeoutMs());

    protected Duration timeout() {
        return timeout;
    }
}
