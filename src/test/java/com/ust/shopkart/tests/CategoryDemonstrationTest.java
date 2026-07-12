package com.ust.shopkart.tests;

import com.ust.shopkart.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class CategoryDemonstrationTest {

    @Test
    @Story("Passed demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("A normal passing test so Allure shows a Passed status")
    void demonstratesPassedCategory() {
        int actualOrderCount = 1;
        int expectedOrderCount = 1;

        assertEquals(expectedOrderCount, actualOrderCount, "Order count should match");
    }

    @Test
    @Story("Product defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails a plain assertion so Allure buckets it under Product defects")
    void demonstratesProductDefectCategory() {
        int actualOrderCount = 1;
        int expectedOrderCount = 2;

        assertEquals(expectedOrderCount, actualOrderCount, "Order count mismatch");
    }

    @Test
    @Story("Test defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately throws an unhandled exception so Allure buckets it under Test defects broken")
    void demonstratesTestDefectCategory() {
        Object nullOrder = null;

        nullOrder.toString();
    }

    @Test
    @Story("Flaky demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails with a message matching the flaky messageRegex")
    void demonstratesFlakyCategory() {
        boolean connectionStable = false;

        if (!connectionStable) {
            throw new AssertionError("Order save failed: connection reset while writing to database");
        }
    }

    @Test
    @Story("Skipped demo")
    @Severity(SeverityLevel.MINOR)
    @Description("Skipped at runtime via a failed assumption, not a hard failure")
    void demonstratesSkippedViaAssumption() {
        Assumptions.assumeTrue(false, "Skipping because this environment doesn't have the required setup");

        assertEquals(1, 1);
    }

    @Test
    @Disabled("Demonstrates Allure's Skipped status for statically disabled tests")
    @Story("Skipped demo")
    @Severity(SeverityLevel.MINOR)
    @Description("Skipped before execution via the Disabled annotation")
    void demonstratesSkippedViaDisabled() {
        assertEquals(1, 1);
    }
}