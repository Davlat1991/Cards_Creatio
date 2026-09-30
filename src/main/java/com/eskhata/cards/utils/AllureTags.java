package com.eskhata.cards.utils;

/**
 * Единый источник Allure-меток. Никаких хардкод-строк в тестах (правило проекта).
 * Расширяй аддитивно по мере появления фич продукта «Карта».
 */
public final class AllureTags {

    private AllureTags() {}

    public static final class Epics {
        private Epics() {}
        public static final String CARDS = "Карты";
    }

    public static final class Features {
        private Features() {}
        public static final String AUTH         = "Авторизация";
        public static final String WORKSPACE    = "Рабочие места";
        public static final String CONSULTATION  = "Консультация";
        public static final String PRODUCT       = "Выбор продукта";
        public static final String E2E          = "E2E-маршрут";
    }

    public static final class Stories {
        private Stories() {}
        public static final String LOGIN             = "Вход в систему";
        public static final String SWITCH_WORKSPACE  = "Смена рабочего места";
        public static final String CLIENT_SEARCH     = "Данные клиента";
        public static final String PRODUCT_SELECTION = "Выбор вида продукта";
    }
}
