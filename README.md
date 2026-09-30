# Cards Autotest — Creatio 8.3 Freedom UI

E2E-автотесты продукта **«Карта»**. LEGO-подход: тесты собираются из переиспользуемых блоков
(`Tests → Flows → Pages → Components → Core`). Отдельный репозиторий от `Platform_Creatio`
и от депозитного спайка (другой продукт, тот же стенд 8.3 Freedom UI).

Каркас перенесён из проверенного спайка: вход, навигация (смена рабочего места),
данные клиента и работа с комбобоксами уже доказаны на живом Freedom UI. Продуктовый
слой (экран карты) достраивается по мере снятия живого DOM.

## Стек
Java 17 · Selenide 7.2.1 · TestNG 7.11.0 · Allure 2.27.0 · RestAssured 5.4.0 · Lombok · SLF4J+Logback · Maven

## Быстрый старт

1. **Конфиг стенда и учётки:**
   ```
   cp src/main/resources/config.properties.example src/main/resources/config.properties
   ```
   Заполни `base.url`, `user.card.1.login`, `user.card.1.password`.
   Реальный `config.properties` в `.gitignore` — в репозиторий не попадёт.

2. **Компиляция:**
   ```
   mvn clean test-compile
   ```

3. **Базовый прогон (вход, смена рабочего места, данные клиента):**
   ```
   mvn test -Psingle
   ```
   Секреты можно передать при запуске:
   ```
   mvn test -Psingle -Duser.card.1.login=ivanov -Duser.card.1.password=***
   ```

4. **Отчёт Allure:**
   ```
   allure serve target/allure-results
   ```

## Профили Maven
`single` (по умолчанию) · `smoke` · `parallel` · `ci`

## Что уже переносится (доказано на Freedom UI)
- `AuthFlow` — вход (экран NuiLogin).
- `WorkspaceFlow` — смена рабочего места (по `data-item-marker`).
- `ConsultationFlow` — данные клиента (поля по `placeholder`, т.к. id/marker = GUID).
- `LookupComponent` — комбобоксы (маркер на обёртке + пункт по тексту).

## Что достраивается под карту
- Продуктовый экран карты (`ProductSelectionPage`, `ProductSelectionSpikeTest`) —
  тест выключен (`enabled=false`), пока не снят живой DOM карты. Порядок:
  1) дойти до заявки карты (Начать консультацию → Оформить → **Карта**),
  2) снять DOM экрана продукта,
  3) подставить локатор/значение, включить тест, прогнать.

## Правило локаторов Freedom UI (проверено на живом DOM 8.3)
- Поля ввода → по `placeholder` (id/marker часто GUID — не использовать).
- Кнопки, панели, поля с осмысленным маркером → по `data-item-marker`.
- Комбобокс → клик по полю раскрывает `div.listview > ul > li`, пункт по тексту.
- Маркер может висеть на div-обёртке, а input — внутри неё (`[marker] input`).
- Никогда не угадывать локатор — снимать с живого DOM (правило №10).

## Правила кода
1. Флоу НЕ наследуют BasePage.
2. Локаторы — методы, не поля.
3. Нет `Thread.sleep` — только `shouldBe/shouldHave` с `Duration`.
4. Нет static-полей с состоянием.
5. Java 17.
6. Пользователи через `UserPool`.
7. Данные — через фабрику.
8. Все методы с `@Step` и `log.info`.
9. Нет `setValue()` — посимвольный `sendKeys` + `Keys.TAB`.
10. Локатор — только по проверенному живому DOM.
11. Изменение общих классов — только аддитивно.
12. `mvn test-compile` после каждого шага; живой прогон перед закрытием задачи.
13. Ассершены обязательны — каждый флоу проверяет результат, а не только кликает.
