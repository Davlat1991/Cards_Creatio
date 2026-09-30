package com.eskhata.cards.testdata.models;

/**
 * Роли пользователей продукта «Карта».
 * Стартовый минимум — оператор фронт-офиса; добавляй роли аддитивно (правило №11).
 */
public enum UserRole {
    CARD_OPERATOR("Оператор карт");

    private final String workspaceTitle;

    UserRole(String workspaceTitle) {
        this.workspaceTitle = workspaceTitle;
    }

    public String workspaceTitle() {
        return workspaceTitle;
    }
}
