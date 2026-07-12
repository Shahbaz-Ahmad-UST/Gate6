package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.AuthClient;
import com.ust.shopkart.api.client.CartClient;
import com.ust.shopkart.api.client.ProductClient;
import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.support.DbSupport;
import com.ust.shopkart.support.TestEnvironment;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TotalCartApiTest{
    private static final String email = TestEnvironment.required("CUSTOMER_1_NAME");
    private static final String password =TestEnvironment.required("CUSTOMER_PASSWORD");

    private static String token;
    private  static long cartId;

    AuthClient login = new AuthClient();
    CartClient cartClient = new CartClient();

    @Test
    @Order(1)
    @DisplayName("Login with valid credentials ")
    void validLogin() {

        Response response = login.login(email,password)
                .then()
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        token = jsonPath.getString("token");
    }

    @Test
    @Order(2)
    @DisplayName("create cart of login user ")
    void createCart() {
        Response response = cartClient.createCart(token)
                .then()
                .statusCode(201)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        cartId = jsonPath.getLong("cartId");
        assertEquals("OPEN",jsonPath.getString("status"));
        assertEquals(0,jsonPath.getInt("totalPaise"));
        assertEquals(0,jsonPath.getList("items").size());
    }

    @Test
    @Order(3)
    @DisplayName("get cart by cartId of login user ")
    void getCart() {
        Response response = cartClient.getCart(cartId,token)
                .then()
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();

        assertEquals("OPEN",jsonPath.getString("status"));
        assertEquals(0,jsonPath.getInt("totalPaise"));
        assertEquals(0,jsonPath.getList("items").size());
    }
    @Test
    @Order(4)
    @DisplayName("Add products to cart and validate total with database")
    void addToCart_ValidateTotal_Price_With_Database() {

        // Add Bottle (Qty = 2)
        cartClient.addItem(cartId, "SKU-BTL", 2,token)
                .then()
                .statusCode(200);

        // Add Bag (Qty = 1)
        Response response = cartClient.addItem(cartId, "SKU-BAG", 1,token)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("OPEN", json.getString("status"));

        // Bottle
        assertEquals(2, json.getInt("items[0].qty"));
        assertEquals(34900, json.getInt("items[0].unitPricePaise"));
        assertEquals(69800, json.getInt("items[0].lineTotalPaise"));

        // Bag
        assertEquals(1, json.getInt("items[1].qty"));
        assertEquals(49900, json.getInt("items[1].unitPricePaise"));
        assertEquals(49900, json.getInt("items[1].lineTotalPaise"));

        // Total Calculation
        int expectedTotal =
                json.getInt("items[0].lineTotalPaise")
                        + json.getInt("items[1].lineTotalPaise");

        assertEquals(expectedTotal, json.getInt("totalPaise"));


        //database verification
        DbSupport db = new DbSupport(DatabaseConfig.fromEnvironmentCredential());


        assertEquals(2, db.getCartItemQuantity(cartId, "SKU-BTL"));
        assertEquals(34900, db.getUnitPrice(cartId, "SKU-BTL"));
        assertEquals(1, db.getCartItemQuantity(cartId, "SKU-BAG"));
        assertEquals(49900, db.getUnitPrice(cartId, "SKU-BAG"));
    }

}