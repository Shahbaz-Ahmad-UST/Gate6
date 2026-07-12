package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.OrderClient;
import com.ust.shopkart.api.client.ProductClient;
import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.model.OrderRow;
import com.ust.shopkart.support.DbSupport;
import com.ust.shopkart.support.TestEnvironment;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutE2ETest {

    private static final String email = TestEnvironment.required("CUSTOMER_2_NAME");

    private static final String password = TestEnvironment.required("CUSTOMER_PASSWORD");

    AuthClient authClient = new AuthClient();
    ProductClient productClient = new ProductClient();
    CartClient cartClient = new CartClient();
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Checkout End-To-End")
    void checkoutE2E() {

        // Login
        String token = authClient.login(email, password)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Search Product
        Response productResponse = productClient.searchProductByKeyword("Metro Carryall")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath product = productResponse.jsonPath();

        String sku = product.getString("[0].sku");

        // Create Cart
        Response cartResponse = cartClient.createCart(token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        long cartId = cartResponse.jsonPath().getLong("cartId");

        // Add Product
        cartClient.addItem(cartId, sku, 1, token)
                .then()
                .statusCode(200);

        // Checkout
        Response orderResponse = orderClient.placeOrder(
                        cartId,
                        "Chennai, Tamil Nadu",
                        token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonPath = orderResponse.jsonPath();

        long orderId = jsonPath.getLong("id");
        int expectedTotal = jsonPath.getInt("totalPaise");
        assertEquals(cartId, jsonPath.getLong("cartId"));
        assertEquals("PLACED", jsonPath.getString("status"));
        assertEquals("Chennai, Tamil Nadu", jsonPath.getString("address"));

        // Get Order
        Response getOrder = orderClient.getOrder(orderId, token)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath orderJson = getOrder.jsonPath();

        assertEquals(orderId, orderJson.getLong("id"));
        assertEquals(cartId, orderJson.getLong("cartId"));
        assertEquals("PLACED", orderJson.getString("status"));
        assertEquals("Chennai, Tamil Nadu", orderJson.getString("address"));

        // Database Verification
        DbSupport db = new DbSupport(DatabaseConfig.fromEnvironmentCredential());

        OrderRow order = db.findOrder(orderId);

        assertEquals(orderId, order.id());
        assertEquals(cartId, order.cartId());
        assertEquals("PLACED", order.status());
        assertEquals(expectedTotal, order.totalPaise());
        assertEquals("Chennai, Tamil Nadu", order.address());
    }
}