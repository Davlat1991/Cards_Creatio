package com.eskhata.cards.tests;

import com.eskhata.cards.testdata.CardProductFactory;
import com.eskhata.cards.testdata.ClientDataFactory;
import com.eskhata.cards.testdata.TestDataLoader;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.CardProductData;
import com.eskhata.cards.testdata.models.ClientData;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Проверка слоя тест-данных (BPM-693) без браузера:
 * JSON читается, алиасы находятся, пул пользователей работает,
 * чувствительные поля не попадают в toString (а значит — в логи и Allure).
 * Осознанно НЕ наследует BaseTest: браузер здесь не нужен.
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.TEST_DATA)
@Owner("Davlat")
public class TestDataTest {

    @Test
    @Story(AllureTags.Stories.TEST_DATA_LOADING)
    @Description("Клиент по умолчанию загружается из JSON со всеми группами полей")
    @Severity(SeverityLevel.CRITICAL)
    public void clientLoadsFromJson() {
        ClientData client = ClientDataFactory.physicalPerson();

        assertFalse(isBlank(client.getLastName()), "Фамилия пустая");
        assertFalse(isBlank(client.getFirstName()), "Имя пустое");
        assertNotNull(client.getAddress(), "Адрес не загрузился (вложенный объект)");
        assertFalse(isBlank(client.getPzlRelation()), "Признак ПЗЛ пустой");
        assertFalse(isBlank(client.getBkiConsent()), "Согласие БКИ пустое");
        assertFalse(isBlank(client.getRiskLevel()), "Уровень риска пустой");
    }

    @Test
    @Story(AllureTags.Stories.TEST_DATA_LOADING)
    @Description("Чувствительные поля клиента не попадают в toString")
    @Severity(SeverityLevel.CRITICAL)
    public void clientSensitiveFieldsHiddenInToString() {
        ClientData client = ClientDataFactory.physicalPerson();
        String text = client.toString();

        assertHidden(text, client.getPassportNumber(), "номер паспорта");
        assertHidden(text, client.getInn(), "ИНН");
        assertHidden(text, client.getSmsPhone(), "телефон");
        assertHidden(text, client.getCodeWord(), "кодовое слово");
        assertHidden(text, client.getBirthDate(), "дата рождения");
        assertTrue(text.contains(client.getLastName()), "ФИО должно оставаться видимым в отчёте");
    }

    @Test
    @Story(AllureTags.Stories.TEST_DATA_LOADING)
    @Description("Продукт из CREATIO-439 загружается из card-products.json")
    @Severity(SeverityLevel.NORMAL)
    public void productLoadsFromJson() {
        CardProductData product = CardProductFactory.visaGoldDebit();

        assertEquals(product.getProductKind(), "Дебетная карта");
        assertEquals(product.getPaymentSystem(), "VISA International");
        assertEquals(product.getTermMonths(), 60);
        assertFalse(isBlank(product.getProductName()), "Название продукта пустое");
    }

    @Test
    @Story(AllureTags.Stories.TEST_DATA_LOADING)
    @Description("Неизвестный алиас даёт понятную ошибку со списком доступных")
    @Severity(SeverityLevel.MINOR)
    public void unknownAliasFailsWithClearMessage() {
        IllegalArgumentException e = expectThrows(IllegalArgumentException.class,
                () -> TestDataLoader.get().client("no-such-client"));
        assertTrue(e.getMessage().contains("no-such-client"), "В ошибке нет имени алиаса");
        assertTrue(e.getMessage().contains(ClientDataFactory.RETAIL_VERIFIED), "В ошибке нет списка доступных алиасов");
    }

    @Test
    @Story(AllureTags.Stories.TEST_DATA_LOADING)
    @Description("UserPool отдаёт пользователя роли, пароль не светится в toString")
    @Severity(SeverityLevel.CRITICAL)
    public void userPoolGivesUserWithoutPasswordInToString() {
        assertTrue(UserPool.size(UserRole.CARD_OPERATOR) >= 1, "Пул CARD_OPERATOR пуст");

        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        assertFalse(isBlank(user.getLogin()), "Логин пустой");
        assertEquals(user.getRole(), UserRole.CARD_OPERATOR);
        assertHidden(user.toString(), user.getPassword(), "пароль");
    }

    private static void assertHidden(String text, String secret, String what) {
        if (!isBlank(secret)) {
            assertFalse(text.contains(secret), "В toString виден " + what);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}