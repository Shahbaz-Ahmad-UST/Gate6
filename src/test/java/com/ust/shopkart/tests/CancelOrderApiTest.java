package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.OrderClient;
import com.ust.shopkart.support.TestEnvironment;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelOrderApiTest {

    private final AuthClient auth = new AuthClient();
    private final CartClient cart = new CartClient();
    private final OrderClient order = new OrderClient();

    @Test
    @DisplayName("Cancel placed order")
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