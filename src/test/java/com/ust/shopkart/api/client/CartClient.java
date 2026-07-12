package com.ust.shopkart.api.client;

import io.restassured.response.Response;

import java.util.Map;

import static com.ust.shopkart.support.SpecFactory.*;
import static io.restassured.RestAssured.given;

public class CartClient {

    public Response createCart(String token) {
        return given()
                .spec(commonJsonRequest)
                .header("Authorization","Bearer "+token)
                .post("/carts");
    }

    public Response getCart(long cartId,String token) {

        return given()
                .spec(commonJsonRequest)
                .header("Authorization","Bearer "+token)
                .pathParam("cartId",cartId)
                .get("/carts/{cartId}");
    }


    public Response addItem(long cartId, String sku, int quantity,String token)
    {
      var requestBody =Map.of("sku", sku,
              "qty", quantity);
        return given()
                .spec(commonJsonRequest)
                .header("Authorization","Bearer "+token)
                .body(requestBody)
                .pathParam("cartId",cartId)
                .post("/carts/{cartId}/items");
    }
}