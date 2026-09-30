package com.eskhata.cards.core.config;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton-конфиг. Читает config.properties из classpath,
 * системные -D... имеют приоритет над файлом.
 * Правило проекта №4: без static-полей с изменяемым состоянием —
 * состояние держим внутри единственного instance.
 */
@Slf4j
public final class Config {

    private static final class Holder {
        private static final Config INSTANCE = new Config();
    }

    public static Config get() {
        return Holder.INSTANCE;
    }

    private final Properties props = new Properties();

    private Config() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
                log.info("config.properties загружен");
            } else {
                log.warn("config.properties не найден в classpath — работаем на -D и дефолтах");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось прочитать config.properties", e);
        }
    }

    private String value(String key, String def) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;
        return props.getProperty(key, def);
    }

    public String baseUrl()      { return value("base.url", ""); }
    public String loginPath()    { return value("login.path", "/Login/NuiLogin.aspx"); }
    public boolean headless()    { return Boolean.parseBoolean(value("headless", "false")); }
    public long timeoutMs()      { return Long.parseLong(value("timeout.ms", "15000")); }
    public String browser()      { return value("browser", "chrome"); }

    /** Дефолтный пользователь-оператор карт (на спайк одного хватает; пул — в UserPool). */
    public String defaultLogin()    { return value("user.card.1.login", ""); }
    public String defaultPassword() { return value("user.card.1.password", ""); }

    /** URL уже существующей заявки — для спайк-теста выбора продукта (полный путь после base.url). */
    public String requestPageUrl()  { return value("request.page.url", ""); }

    public String raw(String key)   { return value(key, ""); }
}
