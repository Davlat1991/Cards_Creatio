package com.eskhata.cards.core.services;


import com.codeborne.selenide.WebElementCondition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.hidden;

/**
 * Явные ожидания для паттернов, которых нет в одном shouldBe/shouldHave.
 * Правило проекта №3: без Thread.sleep — все ожидания ограничены Duration.
 *
 * Уроки 7.18, перенесённые осознанно:
 *  - оверлеи/мини-карточки/затемнение держатся дольше таймаута и перехватывают
 *    клики → нужно явно дождаться ИСЧЕЗНОВЕНИЯ (waitGone);
 *  - состояние приходит асинхронно одним из двух путей → ждать ЛЮБОЕ, что
 *    наступит раньше (waitEither);
 *  - перерисовка DOM делает ранее найденную ссылку недействительной (Stale) →
 *    повтор осмыслен ТОЛЬКО с повторным ПОИСКОМ элемента (retryOnStale);
 *  - ожидания Selenide бросают Error (UIAssertionError), а не Exception → ловим Throwable;
 *  - каждая повторная попытка пишется в журнал (иначе неработающий механизм
 *    неотличим от рабочего).
 */
@Slf4j
public final class WaitService {

    private WaitService() {}

    /** Дождаться исчезновения элемента (оверлей / мини-карточка / затемнение). */
    @Step("Дождаться исчезновения элемента")
    public static void waitGone(SelenideElement element, Duration timeout) {
        element.should(hidden, timeout);
        log.info("Элемент исчез — ожидание завершено");
    }

    /**
     * Дождаться выполнения ЛЮБОГО из двух условий — что наступит раньше.
     * Опрос обоих состояний до истечения timeout, без фиксированных пауз.
     * @return 1 — если первым выполнилось условие a; 2 — если b.
     */
    @Step("Дождаться любого из двух состояний")
    public static int waitEither(SelenideElement a, WebElementCondition condA,
                                 SelenideElement b, WebElementCondition condB,
                                 Duration timeout) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            if (a.has(condA)) { log.info("Наступило состояние A"); return 1; }
            if (b.has(condB)) { log.info("Наступило состояние B"); return 2; }
        }
        throw new IllegalStateException(
                "Ни одно из двух состояний не наступило за " + timeout.toMillis() + " мс");
    }

    /**
     * Повтор действия, устойчивый к StaleElement / перерисовке DOM.
     * ВАЖНО: действие должно ЗАНОВО находить элемент внутри Supplier —
     * переиспользование ранее найденной ссылки обессмысливает повтор.
     * Ограничено по времени (без Thread.sleep): повтор идёт до истечения timeout.
     */
    @Step("Выполнить с повтором при устаревании элемента")
    public static <T> T retryOnStale(Supplier<T> action, Duration timeout) {
        long deadline = System.nanoTime() + timeout.toNanos();
        Throwable last = null;
        int attempt = 0;
        while (System.nanoTime() < deadline) {
            attempt++;
            try {
                return action.get();
            } catch (Throwable t) {               // Selenide бросает Error, не только Exception
                last = t;
                log.info("Попытка {} не удалась: {} — повтор",
                        attempt, t.getClass().getSimpleName());
            }
        }
        throw new IllegalStateException(
                "Действие не удалось за " + timeout.toMillis() + " мс (" + attempt + " попыток)", last);
    }
}