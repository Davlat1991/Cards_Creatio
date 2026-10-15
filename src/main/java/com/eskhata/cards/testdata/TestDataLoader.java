package com.eskhata.cards.testdata;

import com.eskhata.cards.testdata.models.CardProductData;
import com.eskhata.cards.testdata.models.ClientData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;

/**
 * Загрузка тест-данных из classpath (src/test/resources/testdata/).
 * Клиенты: clients.json (локально, в .gitignore) → иначе clients.example.json (шаблон из репо).
 * Продукты: card-products.json (в репо).
 * Читается один раз за прогон; состояние — в единственном instance (правило №4).
 */
@Slf4j
public final class TestDataLoader {

    private static final String DIR = "testdata/";
    private static final String CLIENTS = DIR + "clients.json";
    private static final String CLIENTS_EXAMPLE = DIR + "clients.example.json";
    private static final String PRODUCTS = DIR + "card-products.json";

    private static final class Holder {
        private static final TestDataLoader INSTANCE = new TestDataLoader();
    }

    public static TestDataLoader get() {
        return Holder.INSTANCE;
    }

    private final Map<String, ClientData> clients;
    private final Map<String, CardProductData> products;

    private TestDataLoader() {
        ObjectMapper mapper = new ObjectMapper();
        String clientsFile = exists(CLIENTS) ? CLIENTS : CLIENTS_EXAMPLE;
        if (clientsFile.equals(CLIENTS_EXAMPLE)) {
            log.warn("{} не найден — использую шаблон {} (фейковые данные, на стенде клиента не будет)",
                    CLIENTS, CLIENTS_EXAMPLE);
        }
        this.clients = read(mapper, clientsFile, new TypeReference<>() {});
        this.products = read(mapper, PRODUCTS, new TypeReference<>() {});
        log.info("TestDataLoader: клиентов {} (из {}), продуктов {}", clients.size(), clientsFile, products.size());
    }

    public ClientData client(String alias) {
        return require(clients, alias, "клиент");
    }

    public CardProductData product(String alias) {
        return require(products, alias, "продукт");
    }

    private static <T> T require(Map<String, T> map, String alias, String what) {
        T value = map.get(alias);
        if (value == null) {
            throw new IllegalArgumentException("Не найден " + what + " с алиасом '" + alias
                    + "'. Доступные: " + map.keySet());
        }
        return value;
    }

    private static boolean exists(String path) {
        return TestDataLoader.class.getClassLoader().getResource(path) != null;
    }

    private static <T> Map<String, T> read(ObjectMapper mapper, String path, TypeReference<Map<String, T>> type) {
        try (InputStream in = TestDataLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Файл тест-данных не найден в classpath: " + path);
            }
            return Map.copyOf(mapper.readValue(in, type));
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось прочитать " + path + ": " + e.getMessage(), e);
        }
    }
}