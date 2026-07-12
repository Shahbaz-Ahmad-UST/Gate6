package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.OrderClient;
import com.ust.shopkart.api.client.ProductClient;
import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.model.OrderRow;
import com.ust.shopkart.report.ExtentTestListener;
import com.ust.shopkart.support.DbSupport;
import com.ust.shopkart.support.TestEnvironment;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Shopkart Checkout")
@Feature("Checkout End-To-End")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class CheckoutE2ETest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutE2ETest.class);

    private static final String email = TestEnvironment.required("CUSTOMER_2_NAME");
    private static final String password = TestEnvironment.required("CUSTOMER_PASSWORD");

    AuthClient authClient = new AuthClient();
    ProductClient productClient = new ProductClient();
    CartClient cartClient = new CartClient();
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Checkout End-To-End")
    @Story("Full checkout flow: login, search, cart, order, DB verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Logs in, searches a product, builds a cart, places an order, then verifies both the API response and the underlying database row match")
    void checkoutE2E() {
        log.info("Starting checkoutE2E for customer {}", email);

        String token = login();
        String sku = searchForProduct("Metro Carryall");
        long cartId = createCart(token);
        addItemToCart(cartId, sku, token);

        JsonPath placedOrder = placeOrder(cartId, "Chennai, Tamil Nadu", token);

        long orderId = placedOrder.getLong("id");
        int expectedTotal = placedOrder.getInt("totalPaise");

        assertOrderMatches(placedOrder, cartId, "Chennai, Tamil Nadu");

        JsonPath fetchedOrder = fetchOrder(orderId, token);
        assertEquals(orderId, fetchedOrder.getLong("id"));
        assertOrderMatches(fetchedOrder, cartId, "Chennai, Tamil Nadu");

        verifyOrderInDatabase(orderId, cartId, expectedTotal, "Chennai, Tamil Nadu");

        log.info("Finished checkoutE2E: order {} placed and verified", orderId);
    }

    @Step("Login as {0}")
    private String login() {
        String token = authClient.login(email, password)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        attachEvidence("login-response.txt", "Logged in as " + email);
        return token;
    }

    @Step("Search product by keyword: {0}")
    private String searchForProduct(String keyword) {
        Response productResponse = productClient.searchProductByKeyword(keyword)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath product = productResponse.jsonPath();
        String sku = product.getString("[0].sku");

        attachEvidence("product-search-result.txt", "keyword=" + keyword + ", sku=" + sku);
        log.info("Found product sku={} for keyword={}", sku, keyword);
        return sku;
    }

    @Step("Create cart")
    private long createCart(String token) {
        Response cartResponse = cartClient.createCart(token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        long cartId = cartResponse.jsonPath().getLong("cartId");
        log.info("Created cart {}", cartId);
        return cartId;
    }

    @Step("Add item {1} (sku) to cart {0}")
    private void addItemToCart(long cartId, String sku, String token) {
        cartClient.addItem(cartId, sku, 1, token)
                .then()
                .statusCode(200);

        log.info("Added sku={} to cart {}", sku, cartId);
    }

    @Step("Place order for cart {0} to address {1}")
    private JsonPath placeOrder(long cartId, String address, String token) {
        Response orderResponse = orderClient.placeOrder(cartId, address, token)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonPath = orderResponse.jsonPath();
        attachEvidence("place-order-response.txt", orderResponse.asPrettyString());
        return jsonPath;
    }

    @Step("Fetch order {0}")
    private JsonPath fetchOrder(long orderId, String token) {
        Response getOrder = orderClient.getOrder(orderId, token)
                .then()
                .statusCode(200)
                .extract()
                .response();

        attachEvidence("get-order-response.txt", getOrder.asPrettyString());
        return getOrder.jsonPath();
    }

    @Step("Assert order fields match expected cart {0} and address {1}")
    private void assertOrderMatches(JsonPath order, long cartId, String address) {
        assertEquals(cartId, order.getLong("cartId"));
        assertEquals("PLACED", order.getString("status"));
        assertEquals(address, order.getString("address"));
    }

    @Step("Verify order {0} directly in the database")
    private void verifyOrderInDatabase(long orderId, long cartId, int expectedTotal, String address) {
        DbSupport db = new DbSupport(DatabaseConfig.fromEnvironmentCredential());
        OrderRow order = db.findOrder(orderId);

        attachEvidence("db-order-row.txt",
                "id=" + order.id() + ", cartId=" + order.cartId() + ", status=" + order.status()
                        + ", totalPaise=" + order.totalPaise() + ", address=" + order.address());

        assertEquals(orderId, order.id());
        assertEquals(cartId, order.cartId());
        assertEquals("PLACED", order.status());
        assertEquals(expectedTotal, order.totalPaise());
        assertEquals(address, order.address());
    }

    @Attachment(value = "{name}", type = "text/plain")
    private byte[] attachEvidence(String name, String content) {
        log.debug("Attaching evidence [{}]", name);
        return content.getBytes();
    }
}