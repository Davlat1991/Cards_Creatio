package com.eskhata.cards.testdata.models;

/**
 * Рабочие места (роли) Creatio 8.3 Freedom UI.
 * title = значение data-item-marker кнопки рабочего места в панели навигации
 * (подтверждено на живом DOM). Добавляй аддитивно (правило №11).
 */
public enum Workspace {
    ALL_APPS("Все приложения"),
    CONSULTANT("Консультант"),
    SALES("Продажи"),
    CONSULTANT_KB("Консультант КБ"),
    SERVICE("Сервис"),
    VERIFIER("Верификатор"),
    MARKETING("Маркетинг"),
    SUPERVISOR("Супервизор"),
    CC_OPERATOR("Оператор КЦ"),
    CREDIT("Кредит");

    private final String title;

    Workspace(String title) {
        this.title = title;
    }

    /** Отображаемое имя = значение data-item-marker пункта. */
    public String title() {
        return title;
    }
}
