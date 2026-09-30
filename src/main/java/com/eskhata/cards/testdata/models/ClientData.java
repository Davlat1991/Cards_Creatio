package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.Value;

/** Данные клиента (физлицо) для панели консультации. Immutable, @Builder. */
@Value
@Builder
public class ClientData {
    String lastName;        // Фамилия
    String firstName;       // Имя
    String middleName;      // Отчество
    String birthDate;       // Дата рождения
    String passportSeries;  // Серия
    String passportNumber;  // Номер паспорта
    String inn;             // ИНН
    String contract;        // Договор
}
