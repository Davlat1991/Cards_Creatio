package com.eskhata.cards.testdata;

import com.eskhata.cards.core.config.Config;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пул пользователей с round-robin по ролям (правило №6).
 * Пользователи читаются из config.properties: user.<role.configKey>.1..N.login/.password.
 * Чтение идёт до первого пропущенного номера. Отдельный счётчик на роль, поэтому пул
 * безопасен при параллельном запуске. Состояние живёт в единственном instance (правило №4).
 */
@Slf4j
public final class UserPool {

    private static final class Holder {
        private static final UserPool INSTANCE = new UserPool(Config.get());
    }

    private final Map<UserRole, List<UserCredentials>> users = new EnumMap<>(UserRole.class);
    private final Map<UserRole, AtomicInteger> counters = new EnumMap<>(UserRole.class);

    private UserPool(Config cfg) {
        for (UserRole role : UserRole.values()) {
            List<UserCredentials> list = load(cfg, role);
            users.put(role, List.copyOf(list));
            counters.put(role, new AtomicInteger(0));
            log.info("UserPool: роль {} — пользователей: {}", role, list.size());
        }
    }

    private static List<UserCredentials> load(Config cfg, UserRole role) {
        List<UserCredentials> list = new ArrayList<>();
        for (int i = 1; ; i++) {
            String prefix = "user." + role.configKey() + "." + i + ".";
            String login = cfg.raw(prefix + "login");
            if (login.isBlank()) {
                break;
            }
            list.add(UserCredentials.builder()
                    .login(login)
                    .password(cfg.raw(prefix + "password"))
                    .role(role)
                    .build());
        }
        return list;
    }

    @Step("Взять пользователя из пула: {role}")
    public static UserCredentials next(UserRole role) {
        UserPool pool = Holder.INSTANCE;
        List<UserCredentials> list = pool.users.get(role);
        if (list.isEmpty()) {
            throw new IllegalStateException("Нет пользователей для роли " + role
                    + ": заполни user." + role.configKey() + ".1.login/.password в config.properties");
        }
        int idx = Math.floorMod(pool.counters.get(role).getAndIncrement(), list.size());
        UserCredentials user = list.get(idx);
        log.info("UserPool: роль {} → {} (#{} из {})", role, user.getLogin(), idx + 1, list.size());
        return user;
    }

    /** Сколько пользователей роли загружено (для проверок пула). */
    public static int size(UserRole role) {
        return Holder.INSTANCE.users.get(role).size();
    }
}