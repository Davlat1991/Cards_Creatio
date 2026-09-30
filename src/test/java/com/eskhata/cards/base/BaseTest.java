package com.eskhata.cards.base;

import com.eskhata.cards.core.driver.DriverProvider;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/** База всех тестов: настройка/закрытие драйвера на каждый метод. */
@Slf4j
@Listeners(TestListener.class)
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverProvider.setUp();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverProvider.tearDown();
    }
}
