package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.OrderClient;
import com.ust.shopkart.report.ExtentTestListener;
import com.ust.shopkart.support.TestEnvironment;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Shopkart API")
@Feature("Order Access Control")
@Owner("SHAHBAZ AHMAD")
@ExtendWith(ExtentTestListener.class)
public class OrderAccessApiTest {

    private final AuthClient authClient = new AuthClient();
    private final CartClient cartClient = new CartClient();
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Owner gets 200; another customer gets 403")
    @Story("Only the order owner can view their order; other customers are forbidden")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Alice places an order; Alice can fetch it (200), Bob cannot (403)")
    void ownerCanAccessButOtherCustomerCannot() {

        // Alice Login
        String aliceToken = authClient.login(
                        TestEnvironment.required("CUSTOMER_1_NAME"),
                        TestEnvironment.required("CUSTOMER_PASSWORD"))
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Bob Login
        String bobToken = authClient.login(
                        TestEnvironment.required("CUSTOMER_2_NAME"),
                        TestEnvironment.required("CUSTOMER_PASSWORD"))
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Create Cart
        Response cartResponse = cartClient.createCart(aliceToken)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath cartJson = cartResponse.jsonPath();
        long cartId = cartJson.getLong("cartId");

        // Add Product
        cartClient.addItem(cartId, "SKU-BAG", 1, aliceToken)
                .then()
                .statusCode(200);

        // Place Order
        Response orderResponse = orderClient.placeOrder(
                        cartId,
                        "Chennai, Tamil Nadu",
                        aliceToken)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath orderJson = orderResponse.jsonPath();
        long orderId = orderJson.getLong("id");

        // Owner should access
        orderClient.getOrder(orderId, aliceToken)
                .then()
                .statusCode(200);

        // Another customer should not access
        orderClient.getOrder(orderId, bobToken)
                .then()
                .statusCode(403);
    }
}