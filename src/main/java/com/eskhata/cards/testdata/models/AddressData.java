package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Адрес клиента (анкета, CREATIO-440). Значения — как в UI. Целиком исключается из toString ClientData. */
@Value
@Builder
@Jacksonized
public class AddressData {
    String addressType;   // «Тип адреса»
    String country;       // «Страна»
    String city;          // «Город»
    String street;        // «Улица»
    String house;         // «Дом»
}