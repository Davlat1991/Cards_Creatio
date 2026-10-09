package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/** Учётные данные пользователя. Immutable. Пароль исключён из toString (не светится в Allure/логах). */
@Value
@Builder
public class UserCredentials {
    String login;
    @ToString.Exclude
    String password;
    UserRole role;
}