package com.eskhata.cards.testdata.models;

/**
 * Роли пользователей продукта «Карта».
 * Стартовый минимум — оператор фронт-офиса; добавляй роли аддитивно (правило №11).
 */
public enum UserRole {
    CARD_OPERATOR("Оператор карт", "card");

    private final String workspaceTitle;
    /** Ключ в config.properties: user.<configKey>.<N>.login / .password */
    private final String configKey;

    UserRole(String workspaceTitle, String configKey) {
        this.workspaceTitle = workspaceTitle;
        this.configKey = configKey;
    }

    public String workspaceTitle() {
        return workspaceTitle;
    }

    public String configKey() {
        return configKey;
    }
}