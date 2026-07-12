package com.ust.shopkart.support;

import com.ust.shopkart.config.SelenideConfig;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void beforeScenario() {
        SelenideConfig.configure();
    }
}