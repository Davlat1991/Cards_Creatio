package com.eskhata.cards.tests;

import com.eskhata.cards.base.BaseTest;
import com.eskhata.cards.core.config.Config;
import com.eskhata.cards.flows.common.AuthFlow;
import com.eskhata.cards.flows.common.ProductSelectionFlow;
import com.eskhata.cards.testdata.UserPool;
import com.eskhata.cards.testdata.models.UserCredentials;
import com.eskhata.cards.testdata.models.UserRole;
import com.eskhata.cards.utils.AllureTags;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

/**
 * Блок 3b (спайк): выбор «Вид продукта» на странице заявки КАРТЫ через комбобокс.
 *
 * ВНИМАНИЕ: DOM продуктового экрана карты ещё НЕ снят вживую (правило №10).
 * Поэтому:
 *   - тест выключен (enabled = false), чтобы не ломать зелёную базу нового проекта;
 *   - значение вида продукта — плейсхолдер, заменить на реальное из списка карты;
 *   - локатор поля в ProductSelectionPage (SelectionProductKind) проверить на живом DOM карты:
 *     на депозите маркер висел на div-обёртке, у карты может отличаться.
 * После снятия DOM карты: подставить значение, включить тест (enabled=true), прогнать.
 */
@Epic(AllureTags.Epics.CARDS)
@Feature(AllureTags.Features.PRODUCT)
@Owner("Davlat")
public class ProductSelectionSpikeTest extends BaseTest {

    @Test
    @Story(AllureTags.Stories.PRODUCT_SELECTION)
    @Description("Открыть заявку карты по URL и выбрать «Вид продукта» через комбобокс")
    @Severity(SeverityLevel.CRITICAL)
    public void canSelectCardProductKind() {
        UserCredentials user = UserPool.next(UserRole.CARD_OPERATOR);
        new AuthFlow().login(user);

        String requestUrl = Config.get().requestPageUrl();
        open(requestUrl);

        new ProductSelectionFlow().selectProductKind("Дебетная карта");
    }
}
