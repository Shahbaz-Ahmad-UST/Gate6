package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.OrderClient;
import com.ust.shopkart.report.ExtentTestListener;
import com.ust.shopkart.support.TestEnvironment;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Shopkart API")
@Feature("Order Lifecycle")
@Owner("SHAHBAZ AHMAD")
@ExtendWith(ExtentTestListener.class)
public class CancelOrderApiTest {

    private final AuthClient auth = new AuthClient();
    private final CartClient cart = new CartClient();
    private final OrderClient order = new OrderClient();

    @Test
    @DisplayName("Cancel placed order")
    @Story("Cancelling an order sets status to CANCELLED and blocks a second cancel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Places an order then cancels it, expecting CANCELLED status; cancelling again should be rejected with 409")
    void cancelOrder() {

        // Login
        String token = auth.login(
                        TestEnvironment.required("CUSTOMER_1_NAME"),
                        TestEnvironment.required("CUSTOMER_PASSWORD"))
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Create Cart
        Response cartResponse = cart.createCart(token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        long cartId = cartResponse.jsonPath().getLong("cartId");

        // Add Item
        cart.addItem(cartId, "SKU-BAG", 1, token)
                .then()
                .statusCode(200);

        // Place Order
        Response orderResponse = order.placeOrder(
                        cartId,
                        "TVM, Kerela",
                        token)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();

        long orderId = orderResponse.jsonPath().getLong("id");

        // Cancel Order
        Response response = order.cancelOrder(orderId, token);

        response.then().statusCode(200);

        assertEquals("CANCELLED",
                response.jsonPath().getString("status"));

        // Cancel Again
        order.cancelOrder(orderId, token)
                .then()
                .statusCode(409);
    }
}