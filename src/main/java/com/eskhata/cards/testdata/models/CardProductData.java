package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Параметры карточного продукта (CREATIO-439). Не персональные данные — хранятся в репозитории.
 * Значения — точный текст пунктов выпадающих списков в UI.
 */
@Value
@Builder
@Jacksonized
public class CardProductData {
    String productKind;     // «Вид продукта»: Дебетная карта
    String paymentSystem;   // «Платёжная система»: VISA International
    String currency;        // «Валюта»: Сомони Чумхурии Точикистон
    String tariffType;      // «Тип тарифа»: Общий
    int termMonths;         // «Срок»: 60
    String productName;     // «Продукт»: Visa GOLD - 1680 TJS Общий 5y (in-house)
}