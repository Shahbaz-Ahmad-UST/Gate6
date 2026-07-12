package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.report.ExtentTestListener;
import com.ust.shopkart.support.TestEnvironment;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Shopkart API")
@Feature("Cart Stock Validation")
@Owner("SHAHBAZ AHMAD")
@ExtendWith(ExtentTestListener.class)
public class OutOfStockApiTest {

    private final AuthClient auth = new AuthClient();
    private final CartClient cart = new CartClient();

    @Test
    @DisplayName("Cannot add out-of-stock product")
    @Story("Adding an out-of-stock SKU to the cart is rejected")
    @Severity(SeverityLevel.NORMAL)
    @Description("SKU-CAP has zero stock; adding it to the cart should return 409")
    void addOutOfStockItem() {

        String token = auth.login(
                        TestEnvironment.required("CUSTOMER_1_NAME"),
                        TestEnvironment.required("CUSTOMER_PASSWORD"))
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        Response cartResponse = cart.createCart(token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        long cartId = cartResponse.jsonPath().getLong("cartId");

        cart.addItem(cartId, "SKU-CAP", 1, token)
                .then()
                .statusCode(409);
    }
}