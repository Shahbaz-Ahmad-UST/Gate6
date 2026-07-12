package com.ust.shopkart.config;

import com.codeborne.selenide.Configuration;
import com.ust.shopkart.support.TestEnvironment;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class SelenideConfig {

    private SelenideConfig() {
    }

    public static void configure() {

        // Base URL
        Configuration.baseUrl =
                TestEnvironment.required("BASE_URL");

        // Browser
        Configuration.browser = "chrome";

        // Headless
        Configuration.headless =
                Boolean.parseBoolean(System.getProperty("headless", "false"));

        // Browser size
        Configuration.browserSize = "1920x1080";

        // Timeout
        Configuration.timeout = 10000;

        // Browser lifecycle
        Configuration.holdBrowserOpen = false;
        Configuration.reopenBrowserOnFail = true;

        // Reports
        Configuration.screenshots = true;
        Configuration.savePageSource = true;

        // Uncomment for Selenoid
        // Configuration.remote = "http://localhost:4444/wd/hub";

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // Uncomment for Selenoid

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("name", "Retail Lab UI Tests");

        options.setCapability("selenoid:options", selenoidOptions);

        Configuration.browserCapabilities = options;
    }


    public static String baseUrl() {
        return Configuration.baseUrl;
    }

    public static String loginUrl() {
        return baseUrl() + "/login";
    }

    public static String catalogUrl() {
        return baseUrl();
    }

    public static String cartUrl() {
        return baseUrl() + "/cart";
    }

    public static String checkoutUrl() {
        return baseUrl() + "/checkout";
    }

    public static String orderUrl(){return baseUrl()+"/orders";}
}