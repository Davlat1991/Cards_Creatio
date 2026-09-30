package com.eskhata.cards.testdata.models;

import lombok.Builder;
import lombok.Value;

/** Учётные данные пользователя. Immutable. */
@Value
@Builder
public class UserCredentials {
    String login;
    String password;
    UserRole role;
}
