package com.ust.shopkart.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;
import java.util.regex.Pattern;

public class ExtentTestListener implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        TestWatcher {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static final Pattern FLAKY_PATTERN =
            Pattern.compile(".*(timeout|stale element|connection reset).*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        test.set(extent.createTest(context.getDisplayName()));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        extent.flush();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        test.get().pass("Passed");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String message = cause.getMessage() == null ? "" : cause.getMessage();

        if (FLAKY_PATTERN.matcher(message).matches()) {
            test.get().assignCategory("Flaky");
            test.get().log(Status.WARNING, "Marked FLAKY: " + message);
        } else {
            test.get().fail(cause);
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        test.get().log(Status.SKIP, "Test aborted: " + (cause.getMessage() == null ? cause.toString() : cause.getMessage()));
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExtentTest disabledTest = extent.createTest(context.getDisplayName());
        disabledTest.log(Status.SKIP, "Disabled: " + reason.orElse("no reason given"));
    }

    public static void warn(String message) {
        ExtentTest current = test.get();
        if (current != null) {
            current.log(Status.WARNING, message);
        }
    }
}