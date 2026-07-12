package com.ust.shopkart.api.client;

import io.restassured.response.Response;

import java.util.Map;

import static com.ust.shopkart.support.SpecFactory.commonJsonRequest;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response placeOrder(long cartId, String address, String token) {

        return given()
                .spec(commonJsonRequest)
                .header("Authorization", "Bearer " + token)
                .body(Map.of(
                        "cartId", cartId,
                        "address", address
                ))
                .when()
                .post("/orders");
    }

    public Response getOrder(long orderId, String token) {

        return given()
                .spec(commonJsonRequest)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", orderId)
                .when()
                .get("/orders/{id}");
    }

    public Response cancelOrder(long orderId, String token) {

        return given()
                .spec(commonJsonRequest)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", orderId)
                .when()
                .post("/orders/{id}/cancel");
    }
}