package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.Value;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/** Данные клиента (физлицо) для панели консультации. Immutable, @Builder. */
@Value
@Builder
@Jacksonized
public class ClientData {
    String lastName;        // Фамилия
    String firstName;       // Имя
    String middleName;      // Отчество
    String birthDate;       // Дата рождения
    String passportSeries;  // Серия
    String passportNumber;  // Номер паспорта
    String inn;             // ИНН
    String contract;        // Договор
    /** Сегмент клиента: «Розница». */
    String segment;

    // --- Основной документ (CREATIO-440) ---
    String documentType;          // «Тип документа»
    @ToString.Exclude
    String documentIssueDate;     // дд.ММ.гггг
    @ToString.Exclude
    String documentIssuedBy;      // «Кем выдан»

    // --- Адрес и связь ---
    @ToString.Exclude
    AddressData address;
    @ToString.Exclude
    String smsPhone;              // номер для СМС (клиент верифицирован — предусловие)

    // --- Признаки анкеты: значения как в UI ---
    String pzlRelation;           // связь с ПЗЛ: «Нет»
    String bkiConsent;            // согласие БКИ: «Да»
    String riskLevel;             // уровень риска: «Низкий»
    @ToString.Exclude
    String codeWord;              // кодовое слово
}
