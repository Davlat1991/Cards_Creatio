package com.eskhata.cards.base;

import com.eskhata.cards.utils.AllureAttachments;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Снимает скриншот и page source в Allure при падении теста. */
@Slf4j
public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Тест упал: {}", result.getName(), result.getThrowable());
        AllureAttachments.screenshot();
        AllureAttachments.pageSource();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ Старт теста: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ Тест пройден: {}", result.getName());
    }
}
