package com.eskhata.cards.testdata;

import com.eskhata.cards.core.config.Config;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пул пользователей с round-robin по ролям (правило №6).
 * Отдельный счётчик на каждую роль — для параллельного запуска.
 *
 * На спайк достаточно одного пользователя (user.card.1.*).
 * Пул расширяется добавлением user.card.2..N в config.properties.
 */
public final class UserPool {

    private static final Map<UserRole, List<UserCredentials>> USERS = new ConcurrentHashMap<>();
    private static final Map<UserRole, AtomicInteger> COUNTERS = new ConcurrentHashMap<>();

    static {
        Config cfg = Config.get();
        // Стартовый минимум — один оператор. Дополняй по мере роста пула.
        UserCredentials operator = UserCredentials.builder()
                .login(cfg.defaultLogin())
                .password(cfg.defaultPassword())
                .role(UserRole.CARD_OPERATOR)
                .build();
        USERS.put(UserRole.CARD_OPERATOR, List.of(operator));
        COUNTERS.put(UserRole.CARD_OPERATOR, new AtomicInteger(0));
    }

    private UserPool() {}

    public static UserCredentials next(UserRole role) {
        List<UserCredentials> pool = USERS.get(role);
        if (pool == null || pool.isEmpty()) {
            throw new IllegalStateException("Нет пользователей для роли " + role);
        }
        int idx = Math.floorMod(COUNTERS.get(role).getAndIncrement(), pool.size());
        return pool.get(idx);
    }
}
